package com.kh.trip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomSecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// JWT 기반 인증이므로 CORS 설정을 활성화한다.
				.cors(cors -> {
				})
				// REST API 방식에서는 CSRF를 비활성화한다.
				.csrf(csrf -> csrf.disable())
				// 세션을 사용하지 않고 요청마다 토큰으로 인증한다.
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// 로그인/로그아웃/재발급 경로는 인증 없이 접근 가능하다.
						.requestMatchers("/", "/error", "/api/auth/login", "/api/auth/logout", "/api/auth/refresh")
						.permitAll()
						// JWT 필터 적용 전이므로 현재는 전체 요청을 임시 허용한다.
						.anyRequest().permitAll())
				.formLogin(config -> {
					// 스프링 시큐리티가 로그인 요청을 처리할 URL을 지정한다.
					config.loginProcessingUrl("/api/auth/login");
				});

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
