package com.kh.trip.controller.auth;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.dto.auth.LoginRequestDTO;
import com.kh.trip.dto.auth.LoginResponseDTO;
import com.kh.trip.dto.auth.RefreshTokenRequestDTO;
import com.kh.trip.dto.auth.RegisterRequestDTO;
import com.kh.trip.dto.auth.TokenRefreshResponseDTO;
import com.kh.trip.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	// 인증 관련 비즈니스 로직은 service에게 맡긴다.
	private final AuthService authService;

	@PostMapping("/register")
	public Map<String, String> register(@RequestBody RegisterRequestDTO request) {

		// 회원가입 요청 데이터를 service로 넘긴다.
		authService.register(request);

		// 회원가입 성공 메시지를 응답한다.
		return Map.of("msg", "register success");
	}

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
		// loginId, password를 service로 넘겨 로그인 처리한다.
		// 성공하면 access token, refresh token 등을 응답한다.
		return authService.login(request);
	}

	@PostMapping("/refresh")
	public TokenRefreshResponseDTO refresh(@RequestBody RefreshTokenRequestDTO request) {
		// refresh token을 service로 넘겨 새 access token 발급을 요청한다.
		return authService.refresh(request);
	}

}
