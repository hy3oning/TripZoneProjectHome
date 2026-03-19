package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REVIEWS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reviews")
	@SequenceGenerator(name = "seq_reviews", sequenceName = "SEQ_REVIEWS", allocationSize = 1)
	@Column(name = "REVIEW_NO")
	private Long reviewNo;

	@Column(name = "BOOKING_NO", nullable = false, unique = true)
	private Long bookingNo;

	@Column(name = "USER_NO", nullable = false)
	private Long userNo;

	@Column(name = "LODGING_NO", nullable = false)
	private Long lodgingNo;

	@Column(name = "RATING", nullable = false)
	private Integer rating;

	@Column(name = "CONTENT", nullable = false, length = 2000)
	private String content;

	@Column(name = "HOST_REPLY", length = 2000)
	private String hostReply;

	@Column(name = "HOST_REPLIED_AT")
	private LocalDateTime hostRepliedAt;
}