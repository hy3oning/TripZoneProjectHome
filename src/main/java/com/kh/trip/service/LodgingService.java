package com.kh.trip.service;

import java.util.List;

import com.kh.trip.dto.LodgingDTO;

public interface LodgingService {

	List<LodgingDTO> getList();
	
	Long register(LodgingDTO lodgingDTO);
}
