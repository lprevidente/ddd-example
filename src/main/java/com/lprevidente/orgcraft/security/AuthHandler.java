package com.lprevidente.orgcraft.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
class AuthHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, AuthenticationEntryPoint {

	private final JsonMapper mapper;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest req,
			HttpServletResponse res,
			Authentication authentication) throws IOException {

		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		final var authRes = new AuthRes(true, null);
		mapper.writeValue(res.getWriter(), authRes);
	}

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest req,
			HttpServletResponse res,
			AuthenticationException ex) throws IOException {
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final var authRes = new AuthRes(false, ex.getMessage());
		mapper.writeValue(res.getWriter(), authRes);
	}

	@Override
	public void commence(
			HttpServletRequest req,
			HttpServletResponse res,
			AuthenticationException authException) throws IOException {
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final var authRes = new AuthRes(false, "Authentication required");
		mapper.writeValue(res.getWriter(), authRes);
	}
}
