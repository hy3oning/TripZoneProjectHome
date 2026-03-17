package com.kh.trip.security.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 구글에서 발급한 ID Token을 검증하고
 * 사용자 정보를 추출하는 클래스
 * 
 * - 프론트엔드에서 전달한 구글 로그인용 idToken을 검증
 * - 구글 tokeninfo API를 호출하여 payload 정보 확인
 * - 사용자 식별값(sub), 이메일, 이름을 추출하여 GoogleUserInfo로 반환
 */
@Component
public class GoogleTokenVerifier {

	/**
	 * 구글 ID Token 검증용 API 주소
	 * id_token을 쿼리 파라미터로 전달하여 토큰의 유효성을 확인한다.
	 */
	private static final String GOOGLE_TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";

	@Value("${google.client-id}")
	private String googleClientId;

	/**
	 * 전달받은 Google ID Token을 검증하고 사용자 정보를 반환한다.
	 * 
	 * @param idToken 프론트엔드에서 전달받은 Google ID Token
	 * @return 검증 완료된 Google 사용자 정보 객체
	 * @throws RuntimeException 토큰이 유효하지 않거나 사용자 정보 추출에 실패한 경우
	 */
	public GoogleUserInfo verify(String idToken) {
		try {
			// HTTP 요청을 보내기 위한 RestTemplate 객체 생성
			RestTemplate restTemplate = new RestTemplate();
			GoogleTokenInfoResponse response = restTemplate.getForObject(GOOGLE_TOKEN_INFO_URL + idToken,
					GoogleTokenInfoResponse.class);
			// 응답 자체가 없으면 토큰 검증 실패로 처리
			if (response == null) {
				throw new RuntimeException("Google token verification failed");
			}
			if (response.getAud() == null || !response.getAud().equals(googleClientId)) {
				throw new RuntimeException("Invalid Google token audience");
			}
			// 구글 응답 payload에서 사용자 식별값, 이메일, 이름 추출
			String providerUserId = (String) response.getSub();
			String email = (String) response.getEmail();
			String userName = (String) response.getName();
			// 필수값(sub, email)이 없으면 유효하지 않은 사용자 정보로 판단
			if (providerUserId == null || email == null) {
				throw new RuntimeException("Invalid Google user info");
			}
			// 사용자 이름이 없을 경우 이메일을 기본 이름으로 사용
			return GoogleUserInfo.builder().providerUserId(providerUserId).email(email)
					.userName(userName != null ? userName : email).build();
		} catch (Exception e) {
			// 구글 토큰 검증 과정 중 예외가 발생하면 유효하지 않은 토큰으로 처리
			throw new RuntimeException("Invalid Google idToken");
		}
	}
}
