package com.kh.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
