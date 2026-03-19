package com.kh.trip.domain;

import com.kh.trip.domain.common.BaseTimeEntity;
import com.kh.trip.domain.enums.MemberGradeStatus;

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
@Table(name = "MEMBER_GRADES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberGrade extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member_grades")
	@SequenceGenerator(name = "seq_member_grades", sequenceName = "SEQ_MEMBER_GRADES", allocationSize = 1)
	@Column(name = "GRADE_NO")
	private Long gradeNo;

	@Column(name = "GRADE_CODE", nullable = false, length = 30, unique = true)
	private String gradeCode;

	@Column(name = "GRADE_NAME", nullable = false, length = 50)
	private String gradeName;

	@Builder.Default
	@Column(name = "MIN_TOTAL_AMOUNT", nullable = false)
	private Long minTotalAmount = 0L;

	@Builder.Default
	@Column(name = "MIN_STAY_COUNT", nullable = false)
	private Long minStayCount = 0L;

	@Builder.Default
	@Column(name = "MILEAGE_RATE", nullable = false)
	private Double mileageRate = 0.0;

	@Column(name = "BENEFIT_DESC", length = 500)
	private String benefitDesc;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 20)
	private MemberGradeStatus status = MemberGradeStatus.ACTIVE;

	@Builder.Default
	@Column(name = "SORT_ORDER", nullable = false)
	private Integer sortOrder = 1;
}