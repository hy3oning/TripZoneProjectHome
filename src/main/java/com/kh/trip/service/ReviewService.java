package com.kh.trip.service;

import java.util.List;

import com.kh.trip.dto.ReviewDTO;

public interface ReviewService {

	Long register(ReviewDTO reviewDTO);

	List<ReviewDTO> getListByLodging(Long lodgingNo);

	ReviewDTO getDetail(Long reviewNo);

}
