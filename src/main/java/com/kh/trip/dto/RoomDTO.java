package com.kh.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

	private Long roomNo;
	private Long lodgingNo;
	private String roomName;
	private String roomType;
	private Integer maxGuestCount;
	private Long pricePerNight;
	private Integer roomCount;
	private String description;
	private String status;
}
