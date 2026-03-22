package com.kh.trip.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.dto.BookingDTO;
import com.kh.trip.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@PostMapping
	public Long register(@RequestBody BookingDTO bookingDTO) {

		return bookingService.register(bookingDTO);
	}

	@GetMapping("/user/{userNo}")
	public List<BookingDTO> getListByUser(@PathVariable Long userNo) {
		return bookingService.getListByUser(userNo);
	}

	@GetMapping("/{bookingNo}")
	public BookingDTO getDetail(@PathVariable Long bookingNo) {
		return bookingService.getDetail(bookingNo);
	}

	@PutMapping("/{bookingNo}/cancel")
	public String cancel(@PathVariable Long bookingNo) {
		bookingService.cancel(bookingNo);
		return "success";
	}
}
