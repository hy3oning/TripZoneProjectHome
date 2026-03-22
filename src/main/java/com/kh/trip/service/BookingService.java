package com.kh.trip.service;

import java.util.List;

import com.kh.trip.dto.BookingDTO;

public interface BookingService {

	Long register(BookingDTO bookingDTO);
	
	List<BookingDTO> getListByUser(Long userNo);
	
	BookingDTO getDetail(Long bookingNo);
	
	void cancel(Long bookingNo);
}
