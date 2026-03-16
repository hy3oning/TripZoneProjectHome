package com.kh.trip.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {

	private String loginId;
	private String password;
	private String userName;
	private String email;
	private String phone;
}
