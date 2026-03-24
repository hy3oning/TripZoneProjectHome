package com.kh.trip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.dto.ReviewDTO;
import com.kh.trip.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	public Map<String, Long> register(@RequestBody ReviewDTO reviewDTO) {
		Long reviewNo = reviewService.register(reviewDTO);
		return Map.of("reviewNo", reviewNo);
	}

	@GetMapping("/lodging/{lodgingNo}")
	public List<ReviewDTO> getListByLodging(@PathVariable Long lodgingNo) {
		return reviewService.getListByLodging(lodgingNo);
	}

	@GetMapping("/{reviewNo}")
	public ReviewDTO getDetail(@PathVariable Long reviewNo) {
		return reviewService.getDetail(reviewNo);
	}

}
