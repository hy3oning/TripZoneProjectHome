package com.kh.trip.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.security.AuthUserPrincipal;

@RestController
public class TestController {

	/**
	 * @AuthenticationPrincipal: SecurityContext에 저장된 로그인 사용자 정보를 바로 꺼내준다. 즉, JwtAuthenticationFilter에서 setAuthentication() 한 결과를 받는 것이다.
	 * @param authUser
	 * @return
	 */
	@GetMapping("/api/test")
	public String test(@AuthenticationPrincipal AuthUserPrincipal authUser) {

		return "login success : " + authUser.getLoginId();
	}

}