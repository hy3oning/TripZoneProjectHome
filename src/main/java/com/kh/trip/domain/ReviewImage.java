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
@Table(name = "REVIEW_IMAGES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewImage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_review_images")
	@SequenceGenerator(name = "seq_review_images", sequenceName = "SEQ_REVIEW_IMAGES", allocationSize = 1)
	@Column(name = "REVIEW_IMAGE_NO")
	private Long reviewImageNo;

	@ManyToOne // 리뷰 1개에 이미지 여러장
	@JoinColumn(name = "REVIEW_NO", nullable = false)
	private Review review;

	@Column(name = "IMAGE_URL", nullable = false, length = 300)
	private String imageUrl;

	@Builder.Default
	@Column(name = "SORT_ORDER", nullable = false)
	private Integer sortOrder = 1;
}