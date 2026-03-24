package com.kh.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByLodging_LodgingNo(Long lodgingNo);
}
