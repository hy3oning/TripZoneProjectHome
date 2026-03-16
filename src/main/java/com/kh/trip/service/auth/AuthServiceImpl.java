package com.kh.trip.service.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.trip.domain.User;
import com.kh.trip.domain.UserAuthProvider;
import com.kh.trip.domain.UserRefreshToken;
import com.kh.trip.domain.UserRole;
import com.kh.trip.dto.auth.LoginRequestDTO;
import com.kh.trip.dto.auth.LoginResponseDTO;
import com.kh.trip.dto.auth.RefreshTokenRequestDTO;
import com.kh.trip.dto.auth.RegisterRequestDTO;
import com.kh.trip.dto.auth.TokenRefreshResponseDTO;
import com.kh.trip.repository.UserAuthProviderRepository;
import com.kh.trip.repository.UserRefreshTokenRepository;
import com.kh.trip.repository.UserRepository;
import com.kh.trip.repository.UserRoleRepository;
import com.kh.trip.security.AuthUserPrincipal;
import com.kh.trip.security.CustomUserDetailsService;
import com.kh.trip.security.JwtProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	// 회원 기본정보를 저장하는 repository
	private final UserRepository userRepository;

	// 로그인 정보(loginId, passwordHash)를 저장하는 repository
	private final UserAuthProviderRepository userAuthProviderRepository;

	// 회원 권한 정보를 저장하는 repository
	private final UserRoleRepository userRoleRepository;

	// loginId로 사용자 인증 정보를 조회하는 서비스
	private final CustomUserDetailsService customUserDetailsService;

	// 비밀번호 암호화 / 비밀번호 비교를 위한 객체
	private final PasswordEncoder passwordEncoder;

	// access token, refresh token 생성과 검증을 담당하는 객체
	private final JwtProvider jwtProvider;

	// refresh token을 DB에 저장/조회하는 repository
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	// refresh token 만료 시간(초)
	@Value("${jwt.refresh-token-expiration}")
	private long refreshTokenExpiration;

	@Override
	@Transactional
	public void register(RegisterRequestDTO request) {

		// 이미 사용 중인 loginId면 회원가입 불가
		if (userAuthProviderRepository.existsByLoginId(request.getLoginId())) {
			throw new RuntimeException("LoginId already exists");
		}

		// 이미 사용 중인 email이면 회원가입 불가
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		// USERS 테이블에 저장할 회원 기본정보 생성
		User user = User.builder().userName(request.getUserName()).email(request.getEmail()).phone(request.getPhone())
				.enabled("1").build();

		// 회원 기본정보 저장
		User savedUser = userRepository.save(user);

		// USER_AUTH_PROVIDERS 테이블에 저장할 로그인 정보 생성
		// 비밀번호는 평문이 아니라 암호화해서 저장한다.
		UserAuthProvider authProvider = UserAuthProvider.builder().userNo(savedUser.getUserNo()).providerCode("LOCAL")
				.loginId(request.getLoginId()).passwordHash(passwordEncoder.encode(request.getPassword())).build();

		// 로그인 정보 저장
		userAuthProviderRepository.save(authProvider);

		// USER_ROLES 테이블에 기본 권한 ROLE_USER 저장
		UserRole userRole = UserRole.builder().userNo(savedUser.getUserNo()).roleCode("ROLE_USER").build();

		userRoleRepository.save(userRole);
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {

		// 사용자가 입력한 loginId로 인증 정보를 조회한다.
		AuthUserPrincipal authUser = (AuthUserPrincipal) customUserDetailsService
				.loadUserByUsername(request.getLoginId());

		// 입력한 비밀번호와 DB에 저장된 암호화 비밀번호를 비교한다.
		if (!passwordEncoder.matches(request.getPassword(), authUser.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		// 로그인 성공 시 access token과 refresh token을 생성한다.
		String accessToken = jwtProvider.generateAccessToken(authUser);
		String refreshToken = jwtProvider.generateRefreshToken(authUser);

		// refresh token을 DB에 저장하기 위한 객체 생성
		UserRefreshToken userRefreshToken = UserRefreshToken.builder().userNo(authUser.getUserNo())
				.tokenValue(refreshToken).expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration)).build();

		// refresh token 저장
		userRefreshTokenRepository.save(userRefreshToken);

		// 로그인 성공 응답 데이터 반환
		return LoginResponseDTO.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken)
				.userNo(authUser.getUserNo()).loginId(authUser.getLoginId()).userName(authUser.getUserName())
				.roleNames(authUser.getRoleNames()).build();
	}

	@Override
	public TokenRefreshResponseDTO refresh(RefreshTokenRequestDTO request) {

		// 요청에서 refresh token을 꺼낸다.
		String refreshToken = request.getRefreshToken();

		// 토큰이 정상인지 먼저 확인한다.
		if (!jwtProvider.validateToken(refreshToken)) {
			throw new RuntimeException("Invalid refresh token");
		}

		// DB에 저장된 refresh token인지 확인한다.
		UserRefreshToken savedToken = userRefreshTokenRepository.findByTokenValue(refreshToken)
				.orElseThrow(() -> new RuntimeException("Refresh token not found"));

		// 이미 로그아웃 등으로 폐기된 토큰이면 재발급 불가
		if ("1".equals(savedToken.getRevokedYn())) {
			throw new RuntimeException("Refresh token revoked");
		}

		// DB 기준으로 만료 시간이 지났으면 재발급 불가
		if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Refresh token expired");
		}

		// refresh token 안에 저장된 loginId를 꺼낸다.
		String loginId = jwtProvider.getLoginId(refreshToken);

		// loginId로 사용자 인증 정보를 다시 조회한다.
		AuthUserPrincipal authUser = (AuthUserPrincipal) customUserDetailsService.loadUserByUsername(loginId);

		// 새 access token 생성
		String newAccessToken = jwtProvider.generateAccessToken(authUser);

		// 새 access token만 응답한다.
		return TokenRefreshResponseDTO.builder().grantType("Bearer").accessToken(newAccessToken).build();
	}
}