package com.kh.trip.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.trip.domain.Booking;
import com.kh.trip.domain.Room;
import com.kh.trip.domain.User;
import com.kh.trip.domain.UserCoupon;
import com.kh.trip.domain.enums.BookingStatus;
import com.kh.trip.domain.enums.LodgingStatus;
import com.kh.trip.domain.enums.RoomStatus;
import com.kh.trip.dto.BookingDTO;
import com.kh.trip.repository.BookingRepository;
import com.kh.trip.repository.RoomRepository;
import com.kh.trip.repository.UserCouponRepository;
import com.kh.trip.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;
	private final UserCouponRepository userCouponRepository;

	@Override
	public Long register(BookingDTO bookingDTO) {
		Booking booking = dtoToEntity(bookingDTO);
		Booking result = bookingRepository.save(booking);
		return result.getBookingNo();
	}

	@Override
	public List<BookingDTO> getListByUser(Long userNo) {
		List<Booking> bookingList = bookingRepository.findByUser_UserNo(userNo);
		return bookingList.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public BookingDTO getDetail(Long bookingNo) {

		Booking booking = bookingRepository.findById(bookingNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. bookingNo=" + bookingNo));

		return entityToDTO(booking);
	}

	@Override
	public void cancel(Long bookingNo) {

		Booking booking = bookingRepository.findById(bookingNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. bookingNo=" + bookingNo));

		booking.changeStatus(BookingStatus.CANCELED);

		bookingRepository.save(booking);
	}

	private Booking dtoToEntity(BookingDTO bookingDTO) {

		// 예약상태 중복확인
		List<BookingStatus> blockedStatuses = List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED);

		boolean exists = bookingRepository
				.existsByRoom_RoomNoAndStatusInAndCheckInDateLessThanAndCheckOutDateGreaterThan(bookingDTO.getRoomNo(),
						blockedStatuses, bookingDTO.getCheckOutDate(), bookingDTO.getCheckInDate());

		if (exists) {
			throw new IllegalArgumentException("이미 예약된 객실입니다.");
		}
		User user = userRepository.findById(bookingDTO.getUserNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. userNo=" + bookingDTO.getUserNo()));

		Room room = roomRepository.findById(bookingDTO.getRoomNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실입니다. roomNo=" + bookingDTO.getRoomNo()));

		if (room.getStatus() != RoomStatus.AVAILABLE) {
			throw new IllegalArgumentException("예약할 수 없는 객실입니다.");
		}

		if (room.getLodging().getStatus() != LodgingStatus.ACTIVE) {
			throw new IllegalArgumentException("비활성 숙소의 객실은 예약할 수 없습니다.");
		}

		UserCoupon userCoupon = null;

		if (bookingDTO.getUserCouponNo() != null) {
			userCoupon = userCouponRepository.findById(bookingDTO.getUserCouponNo())
					.orElseThrow(() -> new IllegalArgumentException(
							"존재하지 않는 회원 쿠폰입니다. userCouponNo=" + bookingDTO.getUserCouponNo()));
		}

		return Booking.builder().user(user).room(room).userCoupon(userCoupon).checkInDate(bookingDTO.getCheckInDate())
				.checkOutDate(bookingDTO.getCheckOutDate()).guestCount(bookingDTO.getGuestCount())
				.pricePerNight(bookingDTO.getPricePerNight())
				.discountAmount(bookingDTO.getDiscountAmount() != null ? bookingDTO.getDiscountAmount() : 0L)
				.totalPrice(bookingDTO.getTotalPrice()).requestMessage(bookingDTO.getRequestMessage()).build();
	}

	private BookingDTO entityToDTO(Booking booking) {

		return BookingDTO.builder().bookingNo(booking.getBookingNo()).userNo(booking.getUser().getUserNo())
				.roomNo(booking.getRoom().getRoomNo())
				.userCouponNo(booking.getUserCoupon() != null ? booking.getUserCoupon().getUserCouponNo() : null)
				.checkInDate(booking.getCheckInDate()).checkOutDate(booking.getCheckOutDate())
				.guestCount(booking.getGuestCount()).pricePerNight(booking.getPricePerNight())
				.discountAmount(booking.getDiscountAmount()).totalPrice(booking.getTotalPrice())
				.status(booking.getStatus().name()).requestMessage(booking.getRequestMessage()).build();
	}

}
