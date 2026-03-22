package com.kh.trip.dto;

import java.time.LocalDateTime;

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
public class BookingDTO {
	
	private Long bookingNo;
	private Long userNo;
	private Long roomNo;
	private Long userCouponNo;
	private LocalDateTime checkInDate;
	private LocalDateTime checkOutDate;
	private Integer guestCount;
	private Long pricePerNight;
	private Long discountAmount;
	private Long totalPrice;
	private String status;
	private String requestMessage;

}
