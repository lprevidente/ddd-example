package com.lprevidente.ddd_example.security;

import com.lprevidente.ddd_example.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SecurityConfigIntegrationTest extends BaseIntegrationTest {

  @Nested
  @DisplayName("Unauthenticated access")
  class UnauthenticatedAccess {

    @Test
    @DisplayName("Should return 401 with AuthRes JSON on protected endpoint")
    void shouldReturnUnauthorizedOnProtectedEndpoint() {
      mockMvcTester
          .get()
          .uri("/api/v1/users")
          .exchange()
          .assertThat()
          .hasStatus(HttpStatus.UNAUTHORIZED)
          .hasContentType(MediaType.APPLICATION_JSON)
          .bodyJson()
          .extractingPath("$.signed")
          .asBoolean()
          .isFalse();
    }

    @Test
    @DisplayName("Should include 'Authentication required' reason")
    void shouldIncludeAuthenticationRequiredReason() {
      mockMvcTester
          .get()
          .uri("/api/v1/users")
          .exchange()
          .assertThat()
          .hasStatus(HttpStatus.UNAUTHORIZED)
          .bodyJson()
          .extractingPath("$.reason")
          .asString()
          .isEqualTo("Authentication required");
    }

    @Test
    @DisplayName("Should permit OPTIONS preflight without authentication")
    void shouldPermitOptionsWithoutAuthentication() {
      mockMvcTester
          .options()
          .uri("/api/v1/users")
          .exchange()
          .assertThat()
          .hasStatusOk();
    }
  }

  @Nested
  @DisplayName("POST /login")
  class Login {

    @Test
    @DisplayName("Should return 200 with signed=true on valid credentials")
    void shouldReturnOkOnValidCredentials() {
      mockMvcTester
          .post()
          .uri("/login")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("email", "admin")
          .param("password", "admin")
          .exchange()
          .assertThat()
          .hasStatusOk()
          .hasContentType(MediaType.APPLICATION_JSON)
          .bodyJson()
          .extractingPath("$.signed")
          .asBoolean()
          .isTrue();
    }

    @Test
    @DisplayName("Should return 401 with signed=false on wrong password")
    void shouldReturnUnauthorizedOnWrongPassword() {
      mockMvcTester
          .post()
          .uri("/login")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("email", "admin")
          .param("password", "wrong-password")
          .exchange()
          .assertThat()
          .hasStatus(HttpStatus.UNAUTHORIZED)
          .bodyJson()
          .extractingPath("$.signed")
          .asBoolean()
          .isFalse();
    }

    @Test
    @DisplayName("Should return 401 on unknown user")
    void shouldReturnUnauthorizedOnUnknownUser() {
      mockMvcTester
          .post()
          .uri("/login")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("email", "ghost")
          .param("password", "admin")
          .exchange()
          .assertThat()
          .hasStatus(HttpStatus.UNAUTHORIZED)
          .bodyJson()
          .extractingPath("$.signed")
          .asBoolean()
          .isFalse();
    }

    @Test
    @DisplayName("Should use 'email' parameter instead of default 'username'")
    void shouldUseEmailParameterInsteadOfUsername() {
      mockMvcTester
          .post()
          .uri("/login")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("username", "admin")
          .param("password", "admin")
          .exchange()
          .assertThat()
          .hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Should succeed without CSRF token (CSRF disabled)")
    void shouldSucceedWithoutCsrfToken() {
      mockMvcTester
          .post()
          .uri("/login")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("email", "admin")
          .param("password", "admin")
          .exchange()
          .assertThat()
          .hasStatusOk();
    }
  }
}
