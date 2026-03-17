package com.kh.trip.security.social;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleUserInfo {
	// 구글 사용자 고유 ID
	private String providerUserId;
	// 구글 계정 이메일
	private String email;
	// 구글 계정 이름
	private String userName;
}
