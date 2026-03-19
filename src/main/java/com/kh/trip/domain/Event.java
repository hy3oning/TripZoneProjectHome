package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.EventStatus;

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
@Table(name = "EVENTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Event extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_events")
	@SequenceGenerator(name = "seq_events", sequenceName = "SEQ_EVENTS", allocationSize = 1)
	@Column(name = "EVENT_NO")
	private Long eventNo;

	@Column(name = "ADMIN_USER_NO", nullable = false)
	private Long adminUserNo;

	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	@Column(name = "CONTENT", nullable = false, length = 4000)
	private String content;

	@Column(name = "THUMBNAIL_URL", length = 300)
	private String thumbnailUrl;

	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "END_DATE", nullable = false)
	private LocalDateTime endDate;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "EVENT_STATUS", nullable = false, length = 20)
	private EventStatus eventStatus = EventStatus.DRAFT;

	@Builder.Default
	@Column(name = "VIEW_COUNT", nullable = false)
	private Long viewCount = 0L;
}