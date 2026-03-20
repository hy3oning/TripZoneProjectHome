package com.kh.trip.domain;

import java.time.LocalDateTime;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.UserCouponStatus;

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
@Table(name = "USER_COUPONS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCoupon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_coupons")
	@SequenceGenerator(name = "seq_user_coupons", sequenceName = "SEQ_USER_COUPONS", allocationSize = 1)
	@Column(name = "USER_COUPON_NO")
	private Long userCouponNo;

	@ManyToOne // 유저 1명이 쿠폰 여러개 보유 가능
	@JoinColumn(name = "USER_NO", nullable = false)
	private User user;

	@ManyToOne // 같은 쿠폰 마스터가 여러 회원에게 발급될 수 있음
	@JoinColumn(name = "COUPON_NO", nullable = false)
	private Coupon coupon;

	@Builder.Default
	@Column(name = "ISSUED_AT", nullable = false)
	private LocalDateTime issuedAt = LocalDateTime.now();

	@Column(name = "USED_AT")
	private LocalDateTime usedAt;

	@Column(name = "EXPIRED_AT")
	private LocalDateTime expiredAt;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private UserCouponStatus status = UserCouponStatus.ISSUED;
}