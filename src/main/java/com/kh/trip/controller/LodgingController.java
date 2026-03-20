package com.kh.trip.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.trip.dto.LodgingDTO;
import com.kh.trip.service.LodgingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/lodgings")
@RequiredArgsConstructor
public class LodgingController {

	private final LodgingService lodgingService;

	@GetMapping
	public List<LodgingDTO> getList() {
		return lodgingService.getList();
	}

	@PostMapping
	public Long register(@RequestBody LodgingDTO lodgingDTO) {
		return lodgingService.register(lodgingDTO);
	}
}
