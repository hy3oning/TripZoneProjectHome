package com.kh.trip.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.dto.auth.LoginRequestDTO;
import com.kh.trip.dto.auth.LoginResponseDTO;
import com.kh.trip.dto.auth.RefreshTokenRequestDTO;
import com.kh.trip.dto.auth.TokenRefreshResponseDTO;
import com.kh.trip.service.auth.AuthService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
		return authService.login(request);
	}
	
	@PostMapping("/refresh")
	public TokenRefreshResponseDTO refresh(@RequestBody RefreshTokenRequestDTO request) {
		return authService.refresh(request);
	}
	
	
}
