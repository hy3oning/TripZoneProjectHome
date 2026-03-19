package com.kh.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
