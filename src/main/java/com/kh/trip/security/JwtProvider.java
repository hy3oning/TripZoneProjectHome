package com.kh.trip.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	// JWT 서명에 사용할 비밀키
	private final SecretKey secretKey;
	// access token 만료 시간(ms)
	private final long accessTokenExpiration;
	// refresh token 만료 시간(ms)
	private final long refreshTokenExpiration;

	public JwtProvider(@Value("${jwt.secret}") String secret,
			@Value("${jwt.access-token-expiration}") long accessTokenExpiration,
			@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {

		// properties에서 읽은 secret 문자열을 JWT 서명용 Key 객체로 변환
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		// properties 값은 초 단위이므로 ms로 변환
		this.accessTokenExpiration = accessTokenExpiration * 1000;
		this.refreshTokenExpiration = refreshTokenExpiration * 1000;
	}

	public String generateAccessToken(AuthUserPrincipal authUser) {
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + accessTokenExpiration);

		return Jwts.builder().setSubject(authUser.getLoginId())// subject에는 보통 로그인 식별값을 넣는다.
				// 토큰 안에 사용자 식별/권한 정보를 claim으로 저장
				.claim("userNo", authUser.getUserNo()).claim("userName", authUser.getUserName())
				.claim("roleNames", authUser.getRoleNames()).claim("tokenType", "ACCESS")
				// 발급 시간, 만료 시간 설정
				.setIssuedAt(now).setExpiration(expireDate)
				// 비밀키로 서명하여 위조를 방지
				.signWith(secretKey).compact();
	}

	public String generateRefreshToken(AuthUserPrincipal authUser) {
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + refreshTokenExpiration);

		return Jwts.builder().setSubject(authUser.getLoginId()).claim("userNo", authUser.getUserNo())
				.claim("tokenType", "REFRESH").setIssuedAt(now).setExpiration(expireDate).signWith(secretKey).compact();
	}

	// 토큰에서 payload(Claims) 정보를 꺼내는 공통 메서드
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey) // 검증할때 사용할 비밀키 지정
				.build()
				/**
				 * 토큰 형식이 맞는지 확인
				 * 서명이 맞는지 확인
				 * 만료됐는지 확인
				 * 문제 없으면 payload 읽기
				 */
				.parseClaimsJws(token)
				.getBody();
	}

	// 토큰이 위조되지 않았고 만료되지 않았는지 확인
	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 토큰의 subject(loginId) 값을 꺼냄
	public String getLoginId(String token) {
		return getClaims(token).getSubject();
	}

	// 토큰에 저장한 userNo claim 값을 꺼냄
	public Long getUserNo(String token) {
		return getClaims(token).get("userNo", Long.class);
	}
}
