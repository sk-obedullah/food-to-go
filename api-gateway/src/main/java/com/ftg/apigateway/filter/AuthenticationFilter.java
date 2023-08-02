package com.ftg.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.ftg.apigateway.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtUtil jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = null;
			if (validator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				try {

					jwtUtil.validateToken(authHeader);
					request = exchange.getRequest().mutate().header("currentUser", jwtUtil.extractUsername(authHeader))
							.build();
					Claims claims = jwtUtil.extractAllClaims(authHeader);
					String role = claims.get("role", String.class);
					request = exchange.getRequest().mutate().header("role", role).build();

					if ("ROLE_ADMIN".equals(role)) {
						if (exchange.getRequest().getPath().toString().contains("/api/admin/restaurant-service")) {
							return chain.filter(exchange);
						} else {
							throw new RuntimeException("unauthorized access to the requested service");
						}
					} else if ("ROLE_USER".equals(role)) {
						if (exchange.getRequest().getPath().toString().contains("/api/user/restaurant-service")) {
							return chain.filter(exchange);
						} else {
							throw new RuntimeException("unauthorized access to the requested service");
						}
					} else {
						throw new RuntimeException("unauthorized access to the requested service");
					}

				} catch (Exception e) {
					System.out.println("invalid access...!");
					throw new RuntimeException("un authorized access to application");
				}
			}
			return chain.filter(exchange.mutate().request(request).build());
		});
	}

	public static class Config {

	}
}
