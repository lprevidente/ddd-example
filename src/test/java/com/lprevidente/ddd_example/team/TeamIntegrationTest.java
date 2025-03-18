package com.lprevidente.ddd_example.team;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lprevidente.ddd_example.team.application.command.CreateTeam;
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
@Sql(value = "/team.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TeamIntegrationTest {

  private static final UUID EXISTING_TEAM_ID_UUID =
      UUID.fromString("44444444-4444-4444-4444-444444444444");
  private static final UUID NON_EXISTENT_TEAM_ID_UUID =
      UUID.fromString("99999999-9999-9999-9999-999999999999");

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Nested
  @DisplayName("GET /api/v1/teams")
  class GetTeamsTest {

    @Test
    @DisplayName("Should return all teams")
    void shouldReturnAllTeams() throws Exception {
      mockMvc
          .perform(get("/api/v1/teams"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
          .andExpect(jsonPath("$[*].id", notNullValue()))
          .andExpect(
              jsonPath("$[*].name", hasItems("Development Team", "Marketing Team", "Sales Team")));
    }
  }

  @Nested
  @DisplayName("POST /api/v1/teams")
  class CreateTeamTest {

    @Test
    @DisplayName("Should create team when input is valid")
    void shouldCreateTeamWhenInputIsValid() throws Exception {
      // Arrange
      final var createTeam = new CreateTeam("QA Team");

      mockMvc
          .perform(
              post("/api/v1/teams")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(createTeam)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$", notNullValue()));

      // Verify team is accessible after creation
      mockMvc
          .perform(get("/api/v1/teams"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[*].name", hasItem("QA Team")));
    }

    @Test
    @DisplayName("Should return bad request when name is blank")
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
      // Arrange
      final var createTeam = new CreateTeam("");

      mockMvc
          .perform(
              post("/api/v1/teams")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(createTeam)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when name is null")
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
      // Arrange - create JSON string directly
      final String createTeamJson = "{\"name\":null}";

      mockMvc
          .perform(
              post("/api/v1/teams").contentType(MediaType.APPLICATION_JSON).content(createTeamJson))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when JSON is malformed")
    void shouldReturnBadRequestWhenJsonIsMalformed() throws Exception {
      mockMvc
          .perform(
              post("/api/v1/teams")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{malformed json}"))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  @DisplayName("DELETE /api/v1/teams")
  class DeleteTeamTest {

    @Test
    @DisplayName("Should delete team when team exists")
    void shouldDeleteTeamWhenTeamExists() throws Exception {
      mockMvc
          .perform(delete("/api/v1/teams/{id}", EXISTING_TEAM_ID_UUID))
          .andExpect(status().isNoContent());

      // Verify team list no longer contains the deleted team
      mockMvc
          .perform(get("/api/v1/teams"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[*].name", not(hasItem("Development Team"))));
    }

    @Test
    @DisplayName("Should return 404 when team does not exist")
    void shouldReturnNotFoundWhenTeamDoesNotExist() throws Exception {
      mockMvc
          .perform(delete("/api/v1/teams/{id}", NON_EXISTENT_TEAM_ID_UUID))
          .andExpect(status().isNotFound());
    }
  }
}
