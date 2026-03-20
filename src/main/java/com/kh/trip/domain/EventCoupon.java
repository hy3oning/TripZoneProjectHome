package com.kh.trip.domain;

import com.kh.trip.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "EVENT_COUPONS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventCoupon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_coupons")
	@SequenceGenerator(name = "seq_event_coupons", sequenceName = "SEQ_EVENT_COUPONS", allocationSize = 1)
	@Column(name = "EVENT_COUPON_NO")
	private Long eventCouponNo;

	@ManyToOne // 이벤트 1개에 쿠폰 여러개 
	@JoinColumn(name = "EVENT_NO", nullable = false)
	private Event event;

	@ManyToOne // 쿠폰 한개가 여러 이벤트
	@JoinColumn(name = "COUPON_NO", nullable = false)
	private Coupon coupon;
}