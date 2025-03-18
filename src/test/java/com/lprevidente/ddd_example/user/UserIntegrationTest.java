package com.lprevidente.ddd_example.user;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lprevidente.ddd_example.user.application.command.AddUser;
import com.lprevidente.ddd_example.user.application.command.UpdateUser;
import com.lprevidente.ddd_example.user.domain.UserId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserIntegrationTest {

  private static final UUID EXISTING_USER_ID_UUID =
      UUID.fromString("11111111-1111-1111-1111-111111111111");
  private static final UUID NON_EXISTENT_USER_ID_UUID =
      UUID.fromString("99999999-9999-9999-9999-999999999999");

  private static final UserId EXISTING_USER_ID = new UserId(EXISTING_USER_ID_UUID);
  private static final UserId NON_EXISTENT_USER_ID = new UserId(NON_EXISTENT_USER_ID_UUID);

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Nested
  @DisplayName("GET /api/v1/users")
  class GetUsersTest {

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() throws Exception {
      mockMvc
          .perform(get("/api/v1/users"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
          .andExpect(jsonPath("$[*].id", notNullValue()))
          .andExpect(jsonPath("$[*].firstName", hasItems("Mario", "Anna", "Giuseppe")));
    }
  }

  @Nested
  @DisplayName("GET /api/v1/users/{id}")
  class GetUserByIdTest {

    @Test
    @DisplayName("Should return user when user exists")
    void shouldReturnUserWhenUserExists() throws Exception {
      mockMvc
          .perform(get("/api/v1/users/{id}", EXISTING_USER_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.firstName", is("Mario")))
          .andExpect(jsonPath("$.lastName", is("Rossi")));
    }

    @Test
    @DisplayName("Should return 404 when user does not exist")
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
      mockMvc
          .perform(get("/api/v1/users/{id}", NON_EXISTENT_USER_ID_UUID))
          .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return bad request when ID is invalid")
    void shouldReturnBadRequestWhenIdIsInvalid() throws Exception {
      mockMvc.perform(get("/api/v1/users/invalid-uuid")).andExpect(status().isBadRequest());
    }
  }

  @Nested
  @DisplayName("POST /api/v1/users")
  class CreateUserTest {

    @Test
    @DisplayName("Should create user when input is valid")
    void shouldCreateUserWhenInputIsValid() throws Exception {
      // Arrange
      final var addUser = new AddUser("Paolo", "Neri", "paolo.neri@example.com", "Password@123");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$", notNullValue()));

      // Verify user is accessible after creation
      mockMvc
          .perform(get("/api/v1/users"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[*].firstName", hasItem("Paolo")));
    }

    @Test
    @DisplayName("Should return bad request when firstName is blank")
    void shouldReturnBadRequestWhenFirstNameIsBlank() throws Exception {
      // Arrange
      final var addUser = new AddUser("", "Neri", "paolo.neri@example.com", "Password@123");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when lastName is blank")
    void shouldReturnBadRequestWhenLastNameIsBlank() throws Exception {
      // Arrange
      final var addUser = new AddUser("Paolo", "", "paolo.neri@example.com", "Password@123");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when email is blank")
    void shouldReturnBadRequestWhenEmailIsBlank() throws Exception {
      // Arrange
      final var addUser = new AddUser("Paolo", "Neri", "", "Password@123");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when password is blank")
    void shouldReturnBadRequestWhenPasswordIsBlank() throws Exception {
      // Arrange
      final var addUser = new AddUser("Paolo", "Neri", "paolo.neri@example.com", "");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when password is too weak")
    void shouldReturnBadRequestWhenPasswordIsTooWeak() throws Exception {
      // Arrange
      final var addUser = new AddUser("Paolo", "Neri", "paolo.neri@example.com", "weak");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when email already exists")
    void shouldReturnBadRequestWhenEmailAlreadyExists() throws Exception {
      // Arrange - using an email that already exists in the test data
      final var addUser =
          new AddUser("Duplicate", "User", "mario.rossi@example.com", "Password@123");

      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUser)))
          .andExpect(status().isConflict())
          .andExpect(jsonPath("$.errorCode", equalTo("EMAIL_ALREADY_IN_USE")));
    }

    @Test
    @DisplayName("Should return bad request when JSON is malformed")
    void shouldReturnBadRequestWhenJsonIsMalformed() throws Exception {
      mockMvc
          .perform(
              post("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{malformed json}"))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  @DisplayName("PUT /api/v1/users")
  class UpdateUserTest {

    @Test
    @DisplayName("Should update user when input is valid")
    void shouldUpdateUserWhenInputIsValid() throws Exception {
      // Arrange
      final var updateUser =
          new UpdateUser(EXISTING_USER_ID, "Mario Aggiornato", "Rossi Aggiornato");

      mockMvc
          .perform(
              put("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(updateUser)))
          .andExpect(status().isNoContent());

      // Verify user is updated
      mockMvc
          .perform(get("/api/v1/users/{id}", EXISTING_USER_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.firstName", is("Mario Aggiornato")))
          .andExpect(jsonPath("$.lastName", is("Rossi Aggiornato")));
    }

    @Test
    @DisplayName("Should return 404 when user does not exist")
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
      // Arrange
      final var updateUser = new UpdateUser(NON_EXISTENT_USER_ID, "Nome", "Cognome");

      mockMvc
          .perform(
              put("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(updateUser)))
          .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return bad request when firstName is blank")
    void shouldReturnBadRequestWhenFirstNameIsBlank() throws Exception {
      // Arrange
      final var updateUser = new UpdateUser(EXISTING_USER_ID, "", "Rossi");

      mockMvc
          .perform(
              put("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(updateUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when lastName is blank")
    void shouldReturnBadRequestWhenLastNameIsBlank() throws Exception {
      // Arrange
      final var updateUser = new UpdateUser(EXISTING_USER_ID, "Mario", "");

      mockMvc
          .perform(
              put("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(updateUser)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when id is null")
    void shouldReturnBadRequestWhenIdIsNull() throws Exception {
      // Arrange - create JSON string directly as we can't create a UserId with null
      final String updateUserJson = "{\"id\":null,\"firstName\":\"Mario\",\"lastName\":\"Rossi\"}";

      mockMvc
          .perform(
              put("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(updateUserJson))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when JSON is malformed")
    void shouldReturnBadRequestWhenJsonIsMalformed() throws Exception {
      mockMvc
          .perform(
              put("/api/v1/users")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{malformed json}"))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  @DisplayName("DELETE /api/v1/users/{id}")
  class DeleteUserTest {

    @Test
    @DisplayName("Should delete user when user exists")
    void shouldDeleteUserWhenUserExists() throws Exception {
      mockMvc
          .perform(delete("/api/v1/users/{id}", EXISTING_USER_ID_UUID))
          .andExpect(status().isNoContent());

      mockMvc
          .perform(get("/api/v1/users/{id}", EXISTING_USER_ID_UUID))
          .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent user")
    void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
      mockMvc
          .perform(delete("/api/v1/users/{id}", NON_EXISTENT_USER_ID_UUID))
          .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return bad request when ID is invalid")
    void shouldReturnBadRequestWhenIdIsInvalid() throws Exception {
      mockMvc.perform(delete("/api/v1/users/invalid-uuid")).andExpect(status().isBadRequest());
    }
  }
}
