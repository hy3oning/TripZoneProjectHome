package com.kh.trip.service;

import java.util.List;

import com.kh.trip.dto.LodgingDTO;

public interface LodgingService {
	List<LodgingDTO> getList();
	
	List<LodgingDTO> getInactiveList();

	Long register(LodgingDTO lodgingDTO);

	LodgingDTO getDetail(Long lodgingNo);

	void modify(LodgingDTO lodgingDTO);

	void remove(Long lodgingNo);
	
	void restore(Long lodgingNo);
}
