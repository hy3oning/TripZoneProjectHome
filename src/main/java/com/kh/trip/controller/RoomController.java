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

import com.kh.trip.dto.RoomDTO;
import com.kh.trip.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;

	@PostMapping
	public Long register(@RequestBody RoomDTO roomDTO) {
		return roomService.register(roomDTO);
	}

	@GetMapping("/lodging/{lodgingNo}")
	public List<RoomDTO> getListByLodging(@PathVariable Long lodgingNo) {
		return roomService.getListByLodging(lodgingNo);
	}

	@GetMapping("/{roomNo}")
	public RoomDTO getDetail(@PathVariable Long roomNo) {
		return roomService.getDetail(roomNo);
	}

	@PutMapping("/{roomNo}")
	public String modify(@PathVariable Long roomNo, @RequestBody RoomDTO roomDTO) {
		roomDTO.setRoomNo(roomNo);
		roomService.modify(roomDTO);
		return "success";
	}

	@DeleteMapping("/{roomNo}")
	public String remove(@PathVariable Long roomNo) {
		roomService.remove(roomNo);
		return "success";
	}

	@PutMapping("/{roomNo}/restore")
	public String restore(@PathVariable Long roomNo) {
		roomService.restore(roomNo);
		return "success";
	}
}
