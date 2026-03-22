package com.kh.trip.service;

import java.util.List;

import com.kh.trip.dto.PaymentDTO;

public interface PaymentService {

	Long register(PaymentDTO paymentDTO);
	
	List<PaymentDTO> getListByBooking(Long bookingNo);
	
	PaymentDTO getDetail(Long paymentNo);
	
	void cancel(Long paymentNo);
}
