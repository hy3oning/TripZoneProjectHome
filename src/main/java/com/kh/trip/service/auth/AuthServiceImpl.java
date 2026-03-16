package com.kh.trip.service.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.trip.domain.UserRefreshToken;
import com.kh.trip.dto.auth.LoginRequestDTO;
import com.kh.trip.dto.auth.LoginResponseDTO;
import com.kh.trip.dto.auth.RefreshTokenRequestDTO;
import com.kh.trip.dto.auth.TokenRefreshResponseDTO;
import com.kh.trip.repository.UserRefreshTokenRepository;
import com.kh.trip.security.AuthUserPrincipal;
import com.kh.trip.security.CustomUserDetailsService;
import com.kh.trip.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	// loginId로 사용자 인증 정보를 조회하는 서비스
	private final CustomUserDetailsService customUserDetailsService;
	// 입력 비밀번호와 암호화된 비밀번호를 비교하는 객체
	private final PasswordEncoder passwordEncoder;
	// access token, refresh token을 생성하는 객체
	private final JwtProvider jwtProvider;
	// refresh token 정보를 DB에 저장하는 repository
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	// refresh token 만료시간 (초)
	@Value("${jwt.refresh-token-expiration}")
	private long refreshTokenExpiration;

	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {

		// 사용자가 입력한 loginId로 회원 인증 정보를 조회
		AuthUserPrincipal authUser = (AuthUserPrincipal) customUserDetailsService
				.loadUserByUsername(request.getLoginId());

		// 입력한 비밀번호와 DB에 저장된 암호화 비밀번호를 비교
		if (!passwordEncoder.matches(request.getPassword(), authUser.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		// 로그인 성공 시 access token, refresh token 생성
		String accessToken = jwtProvider.generateAccessToken(authUser);
		String refreshToken = jwtProvider.generateRefreshToken(authUser);

		// refresh token 을 DB에 저장할 엔티티 생성
		UserRefreshToken userRefreshToken = UserRefreshToken.builder().userNo(authUser.getUserNo())
				.tokenValue(refreshToken).expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration)).build();

		// refresh token 저장
		userRefreshTokenRepository.save(userRefreshToken);

		// 로그인 응답 DTO로 변환하여 반환
		return LoginResponseDTO.builder()
				// Bearer는 토큰 인증 방식 이름
				.grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).userNo(authUser.getUserNo())
				.loginId(authUser.getLoginId()).userName(authUser.getUserName()).roleNames(authUser.getRoleNames())
				.build();
	}

	@Override
	public TokenRefreshResponseDTO refresh(RefreshTokenRequestDTO request) {
		// 요청에서 refresh token 문자열 추출
		String refreshToken = request.getRefreshToken();

		// JWT 형식이 정상인지 확인
		if (!jwtProvider.validateToken(refreshToken)) {
			throw new RuntimeException("Invalid refresh token");
		}

		// DB에 저장된 refresh token 정보 조회
		UserRefreshToken savedToken = userRefreshTokenRepository.findByTokenValue(refreshToken)
				.orElseThrow(() -> new RuntimeException("Refresh token not found"));

		// 이미 로그아웃 등으로 폐기된 토큰인지 확인
		if ("1".equals(savedToken.getRevokedYn())) {
			throw new RuntimeException("Refresh token revoked");
		}

		// DB 기준 만료일이 지났는지 확인
		if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Refresh token expired");
		}

		// 토큰 안에 저장된 loginId 추출
		String loginId = jwtProvider.getLoginId(refreshToken);

		// loginId로 사용자 인증 정보 다시 조회
		AuthUserPrincipal authUser = (AuthUserPrincipal) customUserDetailsService.loadUserByUsername(loginId);

		// 새 access token 생성
		String newAccessToken = jwtProvider.generateAccessToken(authUser);

		// access token만 다시 응답
		return TokenRefreshResponseDTO.builder().grantType("Bearer").accessToken(newAccessToken).build();
	}
}
