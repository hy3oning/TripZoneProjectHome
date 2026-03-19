package com.kh.trip.domain;

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

	@Column(name = "EVENT_NO", nullable = false)
	private Long eventNo;

	@Column(name = "COUPON_NO", nullable = false)
	private Long couponNo;
}