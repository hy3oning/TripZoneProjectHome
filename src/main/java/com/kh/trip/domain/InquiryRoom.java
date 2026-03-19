package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.InquiryRoomStatus;
import com.kh.trip.domain.enums.InquiryTargetType;
import com.kh.trip.domain.enums.InquiryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "INQUIRY_ROOMS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InquiryRoom extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inquiry_rooms")
	@SequenceGenerator(name = "seq_inquiry_rooms", sequenceName = "SEQ_INQUIRY_ROOMS", allocationSize = 1)
	@Column(name = "INQUIRY_ROOM_NO")
	private Long inquiryRoomNo;

	@Column(name = "USER_NO", nullable = false)
	private Long userNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "TARGET_TYPE", nullable = false, length = 20)
	private InquiryTargetType targetType;

	@Enumerated(EnumType.STRING)
	@Column(name = "INQUIRY_TYPE", nullable = false, length = 30)
	private InquiryType inquiryType;

	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	@Column(name = "ADMIN_USER_NO")
	private Long adminUserNo;

	@Column(name = "LODGING_NO")
	private Long lodgingNo;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private InquiryRoomStatus status = InquiryRoomStatus.OPEN;

	@Column(name = "LAST_MESSAGE_AT")
	private LocalDateTime lastMessageAt;
}