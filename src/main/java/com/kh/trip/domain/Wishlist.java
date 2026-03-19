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
@Table(name = "WISHLISTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Wishlist extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_wishlists")
	@SequenceGenerator(name = "seq_wishlists", sequenceName = "SEQ_WISHLISTS", allocationSize = 1)
	@Column(name = "WISHLIST_NO")
	private Long wishlistNo;

	@Column(name = "USER_NO", nullable = false)
	private Long userNo;

	@Column(name = "LODGING_NO", nullable = false)
	private Long lodgingNo;
}