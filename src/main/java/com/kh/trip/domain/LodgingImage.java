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
@Table(name = "LODGING_IMAGES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LodgingImage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lodging_images")
	@SequenceGenerator(name = "seq_lodging_images", sequenceName = "SEQ_LODGING_IMAGES", allocationSize = 1)
	@Column(name = "IMAGE_NO")
	private Long imageNo;

	@ManyToOne // 숙소 1개에 이미지 여러장
	@JoinColumn(name = "LODGING_NO", nullable = false)
	private Lodging lodging;

	@Column(name = "IMAGE_URL", nullable = false, length = 300)
	private String imageUrl;

	@Builder.Default
	@Column(name = "SORT_ORDER", nullable = false)
	private Integer sortOrder = 1;
}
