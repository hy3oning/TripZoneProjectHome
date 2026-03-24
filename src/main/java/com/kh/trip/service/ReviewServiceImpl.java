package com.kh.trip.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.trip.domain.Booking;
import com.kh.trip.domain.Lodging;
import com.kh.trip.domain.Review;
import com.kh.trip.domain.User;
import com.kh.trip.dto.ReviewDTO;
import com.kh.trip.repository.BookingRepository;
import com.kh.trip.repository.LodgingRepository;
import com.kh.trip.repository.ReviewRepository;
import com.kh.trip.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final LodgingRepository lodgingRepository;

	@Override
	public Long register(ReviewDTO reviewDTO) {
		Review review = dtoToEntity(reviewDTO);
		Review result = reviewRepository.save(review);
		return result.getReviewNo();
	}

	@Override
	public List<ReviewDTO> getListByLodging(Long lodgingNo) {
		List<Review> reviewList = reviewRepository.findByLodging_LodgingNo(lodgingNo);
		return reviewList.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public ReviewDTO getDetail(Long reviewNo) {
		Review review = reviewRepository.findById(reviewNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다. reviewNo =" + reviewNo));

		return entityToDTO(review);
	}

	private Review dtoToEntity(ReviewDTO reviewDTO) {
		Booking booking = bookingRepository.findById(reviewDTO.getBookingNo()).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 예약입니다.bookingNo = " + reviewDTO.getBookingNo()));
		User user = userRepository.findById(reviewDTO.getUserNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userNO=" + reviewDTO.getUserNo()));

		Lodging lodging = lodgingRepository.findById(reviewDTO.getLodgingNo()).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 숙소입니다. lodgingNo=" + reviewDTO.getLodgingNo()));

		return Review.builder().booking(booking).user(user).lodging(lodging).rating(reviewDTO.getRating())
				.content(reviewDTO.getContent()).hostReply(reviewDTO.getHostReply())
				.hostRepliedAt(reviewDTO.getHostRepliedAt()).build();
	}

	private ReviewDTO entityToDTO(Review review) {
		return ReviewDTO.builder().reviewNo(review.getReviewNo()).bookingNo(review.getBooking().getBookingNo())
				.userNo(review.getUser().getUserNo()).lodgingNo(review.getLodging().getLodgingNo())
				.rating(review.getRating()).content(review.getContent()).hostReply(review.getHostReply())
				.hostRepliedAt(review.getHostRepliedAt()).build();
	}

}
