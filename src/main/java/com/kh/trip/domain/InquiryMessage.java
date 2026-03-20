package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.InquirySenderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INQUIRY_MESSAGES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InquiryMessage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inquiry_messages")
	@SequenceGenerator(name = "seq_inquiry_messages", sequenceName = "SEQ_INQUIRY_MESSAGES", allocationSize = 1)
	@Column(name = "MESSAGE_NO")
	private Long messageNo;

	@ManyToOne // 문의방 1개에 메시지 여러개
	@JoinColumn(name = "INQUIRY_ROOM_NO", nullable = false)
	private InquiryRoom inquiryRoom;

	@ManyToOne // 유저 1명이 메시지 여러 개
	@JoinColumn(name = "SENDER_USER_NO", nullable = false)
	private User senderUser;

	@Enumerated(EnumType.STRING)
	@Column(name = "SENDER_TYPE", nullable = false, length = 20)
	private InquirySenderType senderType;

	@Column(name = "MESSAGE_CONTENT", length = 2000)
	private String messageContent;

	@Column(name = "IMAGE_URL", length = 300)
	private String imageUrl;

	@Builder.Default
	@Column(name = "READ_YN", nullable = false, length = 1)
	private String readYn = "0";

	@Column(name = "READ_AT")
	private LocalDateTime readAt;
}
