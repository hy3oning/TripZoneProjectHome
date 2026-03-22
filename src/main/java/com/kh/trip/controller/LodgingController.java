package com.kh.trip.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/inactive")
	public List<LodgingDTO> getInactiveList() {
		return lodgingService.getInactiveList();
	}

	@PostMapping
	public Long register(@RequestBody LodgingDTO lodgingDTO) {
		return lodgingService.register(lodgingDTO);
	}

	@GetMapping("/{lodgingNo}")
	public LodgingDTO getDetail(@PathVariable Long lodgingNo) {
		return lodgingService.getDetail(lodgingNo);
	}

	@PutMapping("/{lodgingNo}")
	public String modify(@PathVariable Long lodgingNo, @RequestBody LodgingDTO lodgingDTO) {
		lodgingDTO = LodgingDTO.builder().lodgingNo(lodgingNo).hostNo(lodgingDTO.getHostNo())
				.lodgingName(lodgingDTO.getLodgingName()).lodgingType(lodgingDTO.getLodgingType())
				.region(lodgingDTO.getRegion()).address(lodgingDTO.getAddress())
				.detailAddress(lodgingDTO.getDetailAddress()).zipCode(lodgingDTO.getZipCode())
				.latitude(lodgingDTO.getLatitude()).longitude(lodgingDTO.getLongitude())
				.description(lodgingDTO.getDescription()).checkInTime(lodgingDTO.getCheckInTime())
				.checkOutTime(lodgingDTO.getCheckOutTime()).status(lodgingDTO.getStatus()).build();

		lodgingService.modify(lodgingDTO);
		return "success";
	}

	@DeleteMapping("/{lodgingNo}")
	public String remove(@PathVariable Long lodgingNo) {
		lodgingService.remove(lodgingNo);
		return "success";
	}

	@PutMapping("/{lodgingNo}/restore")
	public String restore(@PathVariable Long lodgingNo) {
		lodgingService.restore(lodgingNo);
		return "success";
	}
}
