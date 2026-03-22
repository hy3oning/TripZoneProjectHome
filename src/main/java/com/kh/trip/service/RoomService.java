package com.kh.trip.service;

import java.util.List;

import com.kh.trip.dto.RoomDTO;

public interface RoomService {

	Long register(RoomDTO roomDTO);

	List<RoomDTO> getListByLodging(Long lodgingNo);

	RoomDTO getDetail(Long roomNo);
	
	void modify(RoomDTO roomDTO);
	
	void remove(Long roomNo);
	void restore(Long roomNo);
}
