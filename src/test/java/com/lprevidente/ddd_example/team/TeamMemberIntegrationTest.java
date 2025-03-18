package com.lprevidente.ddd_example.team;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
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
@Sql(
    value = {"/users.sql", "/team.sql", "/team_members.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TeamMemberIntegrationTest {

  private static final UUID EXISTING_TEAM_ID_UUID =
      UUID.fromString("44444444-4444-4444-4444-444444444444");
  private static final UUID SALES_TEAM_ID_UUID =
      UUID.fromString("66666666-6666-6666-6666-666666666666");
  private static final UUID NON_EXISTENT_TEAM_ID_UUID =
      UUID.fromString("99999999-9999-9999-9999-999999999999");
  private static final UUID EXISTING_USER_ID_UUID =
      UUID.fromString("11111111-1111-1111-1111-111111111111");
  private static final UUID NEW_USER_ID_UUID =
      UUID.fromString("33333333-3333-3333-3333-333333333333");
  private static final UUID NON_EXISTENT_USER_ID_UUID =
      UUID.fromString("88888888-8888-8888-8888-888888888888");

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Nested
  @DisplayName("GET /api/teams/{teamId}/members")
  class GetTeamMembersTest {

    @Test
    @DisplayName("Should return all members of a team")
    void shouldReturnAllMembersOfTeam() throws Exception {
      mockMvc
          .perform(get("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return empty array for team with no members")
    void shouldReturnEmptyArrayForTeamWithNoMembers() throws Exception {
      mockMvc
          .perform(get("/api/teams/{teamId}/members", SALES_TEAM_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should return empty array for non-existent team")
    void shouldReturnEmptyArrayForNonExistentTeam() throws Exception {
      mockMvc
          .perform(get("/api/teams/{teamId}/members", NON_EXISTENT_TEAM_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(0)));
    }
  }

  @Nested
  @DisplayName("POST /api/teams/{teamId}/members")
  class AddTeamMemberTest {

    @Test
    @DisplayName("Should add user to team when input is valid")
    void shouldAddUserToTeamWhenInputIsValid() throws Exception {
      final var addUserToTeam = new AddUserToTeam(SALES_TEAM_ID_UUID, NEW_USER_ID_UUID);

      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", SALES_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUserToTeam)))
          .andExpect(status().isCreated());

      // Verify user is now in the team
      mockMvc
          .perform(get("/api/teams/{teamId}/members", SALES_TEAM_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Should return bad request when user is already a member")
    void shouldReturnBadRequestWhenUserIsAlreadyMember() throws Exception {
      final var addUserToTeam = new AddUserToTeam(EXISTING_TEAM_ID_UUID, EXISTING_USER_ID_UUID);

      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUserToTeam)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when user ID is null")
    void shouldReturnBadRequestWhenUserIdIsNull() throws Exception {
      final String addUserJson = "{\"teamId\":\"" + EXISTING_TEAM_ID_UUID + "\",\"userId\":null}";

      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(addUserJson))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when team ID is null")
    void shouldReturnBadRequestWhenTeamIdIsNull() throws Exception {
      final String addUserJson = "{\"teamId\":null,\"userId\":\"" + NEW_USER_ID_UUID + "\"}";

      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(addUserJson))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when JSON is malformed")
    void shouldReturnBadRequestWhenJsonIsMalformed() throws Exception {
      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{malformed json}"))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when user does not exist")
    void shouldReturnBadRequestWhenUserDoesNotExist() throws Exception {
      final var addUserToTeam = new AddUserToTeam(EXISTING_TEAM_ID_UUID, NON_EXISTENT_USER_ID_UUID);

      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUserToTeam)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when team does not exist")
    void shouldReturnBadRequestWhenTeamDoesNotExist() throws Exception {
      final var addUserToTeam = new AddUserToTeam(NON_EXISTENT_TEAM_ID_UUID, NEW_USER_ID_UUID);

      mockMvc
          .perform(
              post("/api/teams/{teamId}/members", NON_EXISTENT_TEAM_ID_UUID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(addUserToTeam)))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  @DisplayName("DELETE /api/teams/{teamId}/members/{userId}")
  class RemoveTeamMemberTest {

    @Test
    @DisplayName("Should remove user from team when user is a member")
    void shouldRemoveUserFromTeamWhenUserIsMember() throws Exception {
      mockMvc
          .perform(
              delete(
                  "/api/teams/{teamId}/members/{userId}",
                  EXISTING_TEAM_ID_UUID,
                  EXISTING_USER_ID_UUID))
          .andExpect(status().isNoContent());

      // Verify user is no longer in the team
      mockMvc
          .perform(get("/api/teams/{teamId}/members", EXISTING_TEAM_ID_UUID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Should return not found when user is not a member")
    void shouldReturnNotFoundWhenUserIsNotMember() throws Exception {
      mockMvc
          .perform(
              delete(
                  "/api/teams/{teamId}/members/{userId}",
                  SALES_TEAM_ID_UUID,
                  EXISTING_USER_ID_UUID))
          .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return not found when team does not exist")
    void shouldReturnNotFoundWhenTeamDoesNotExist() throws Exception {
      mockMvc
          .perform(
              delete(
                  "/api/teams/{teamId}/members/{userId}",
                  NON_EXISTENT_TEAM_ID_UUID,
                  EXISTING_USER_ID_UUID))
          .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return not found when user does not exist")
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
      mockMvc
          .perform(
              delete(
                  "/api/teams/{teamId}/members/{userId}",
                  EXISTING_TEAM_ID_UUID,
                  NON_EXISTENT_USER_ID_UUID))
          .andExpect(status().isNotFound());
    }
  }
}
