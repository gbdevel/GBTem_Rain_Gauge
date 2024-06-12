package com.gbsoft.rainfallcollector.config.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 최상위 순서.
public class CorsFilter extends OncePerRequestFilter {

	private static final String[] ALLOWED_ORIGINS = {

	};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String origin = request.getHeader("Origin");
		if (origin == null) {
			origin = "http://localhost:3000";
		}

		if (Arrays.asList(ALLOWED_ORIGINS).contains(origin)) {
			response.setHeader("Access-Control-Allow-Origin", origin);
		}

		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
			"Origin, X-Requested-With, Content-Type, Accept, Authorization, refreshToken, Set-Cookie, x-api-key");

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			filterChain.doFilter(request, response);
		}
	}
}