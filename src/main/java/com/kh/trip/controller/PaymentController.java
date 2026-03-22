package com.kh.trip.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.dto.PaymentDTO;
import com.kh.trip.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	public Long register(@RequestBody PaymentDTO paymentDTO) {
		return paymentService.register(paymentDTO);
	}

	@GetMapping("/booking/{bookingNo}")
	public List<PaymentDTO> getListByBooking(@PathVariable Long bookingNo) {
		return paymentService.getListByBooking(bookingNo);
	}

	@GetMapping("/{paymentNo}")
	public PaymentDTO getDetail(@PathVariable Long paymentNo) {
		return paymentService.getDetail(paymentNo);
	}

	@PutMapping("/{paymentNo}/cancel")
	public String cancel(@PathVariable Long paymentNo) {
		paymentService.cancel(paymentNo);
		return "success";
	}
}
