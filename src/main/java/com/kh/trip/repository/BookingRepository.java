package com.kh.trip.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Booking;
import com.kh.trip.domain.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser_UserNo(Long userNo);
	
	boolean existsByRoom_RoomNoAndStatusInAndCheckInDateLessThanAndCheckOutDateGreaterThan(
			Long roomNo,
			List<BookingStatus> statuses,
			LocalDateTime checkOutDate,
			LocalDateTime checkInDate
	);
}
