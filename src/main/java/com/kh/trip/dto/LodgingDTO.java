package com.kh.trip.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LodgingDTO {

	private Long lodgingNo;
	private Long hostNo;
	private String lodgingName;
	private String lodgingType;
	private String region;
	private String address;
	private String detailAddress;
	private String zipCode;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String description;
	private String checkInTime;
	private String checkOutTime;
	private String status;
}
