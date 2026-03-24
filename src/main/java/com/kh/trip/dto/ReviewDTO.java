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
public class ReviewDTO {

	private Long reviewNo;
	private Long bookingNo;
	private Long userNo;
	private Long lodgingNo;
	private Integer rating;
	private String content;
	private String hostReply;
	private LocalDateTime hostRepliedAt;
}
