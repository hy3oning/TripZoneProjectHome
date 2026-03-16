package com.kh.trip.service.auth;

import com.kh.trip.dto.auth.LoginRequestDTO;
import com.kh.trip.dto.auth.LoginResponseDTO;
import com.kh.trip.dto.auth.RefreshTokenRequestDTO;
import com.kh.trip.dto.auth.TokenRefreshResponseDTO;

public interface AuthService {

	LoginResponseDTO login(LoginRequestDTO request);

	TokenRefreshResponseDTO refresh(RefreshTokenRequestDTO request);

}
