package com.kh.trip.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.trip.domain.Booking;
import com.kh.trip.domain.Payment;
import com.kh.trip.domain.enums.PaymentMethod;
import com.kh.trip.domain.enums.PaymentStatus;
import com.kh.trip.dto.PaymentDTO;
import com.kh.trip.repository.BookingRepository;
import com.kh.trip.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
	private final PaymentRepository paymentRepository;
	private final BookingRepository bookingRepository;

	@Override
	public Long register(PaymentDTO paymentDTO) {
		
		List<PaymentStatus> blockedStatuses = List.of(PaymentStatus.READY, PaymentStatus.PAID);

		boolean exists = paymentRepository.existsByBooking_BookingNoAndPaymentStatusIn(paymentDTO.getBookingNo(),
				blockedStatuses);

		if (exists) {
			throw new IllegalArgumentException("이미 진행 중이거나 완료된 결제가 있습니다.");
		}

		Payment payment = dtoToEntity(paymentDTO);
		Payment result = paymentRepository.save(payment);

		return result.getPaymentNo();
	}

	@Override
	public List<PaymentDTO> getListByBooking(Long bookingNo) {

		List<Payment> paymentList = paymentRepository.findByBooking_BookingNoOrderByPaymentNoDesc(bookingNo);

		return paymentList.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public PaymentDTO getDetail(Long paymentNo) {

		Payment payment = paymentRepository.findById(paymentNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제입니다. paymentNo=" + paymentNo));

		return entityToDTO(payment);
	}

	@Override
	public void cancel(Long paymentNo) {

		Payment payment = paymentRepository.findById(paymentNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제입니다. paymentNo=" + paymentNo));

		payment.changePaymentStatus(PaymentStatus.CANCELLED);
		payment.changeCanceledAt(LocalDateTime.now());
		payment.changeRefundAmount(payment.getPaymentAmount());

		paymentRepository.save(payment);
	}

	private Payment dtoToEntity(PaymentDTO paymentDTO) {

		Booking booking = bookingRepository.findById(paymentDTO.getBookingNo()).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 예약입니다. bookingNo=" + paymentDTO.getBookingNo()));

		String orderName = booking.getRoom().getLodging().getLodgingName() + " 예약";

		return Payment.builder().booking(booking).paymentId(paymentDTO.getPaymentId()).storeId(paymentDTO.getStoreId())
				.channelKey(paymentDTO.getChannelKey()).orderName(orderName)
				.paymentAmount(paymentDTO.getPaymentAmount())
				.currency(paymentDTO.getCurrency() != null ? paymentDTO.getCurrency() : "KRW")
				.payMethod(PaymentMethod.valueOf(paymentDTO.getPayMethod())).pgProvider(paymentDTO.getPgProvider())
				.paymentStatus(
						paymentDTO.getPaymentStatus() != null ? PaymentStatus.valueOf(paymentDTO.getPaymentStatus())
								: PaymentStatus.READY)
				.approvedAt(paymentDTO.getApprovedAt()).canceledAt(paymentDTO.getCanceledAt())
				.refundAmount(paymentDTO.getRefundAmount() != null ? paymentDTO.getRefundAmount() : 0L)
				.failReason(paymentDTO.getFailReason()).rawResponse(paymentDTO.getRawResponse()).build();
	}

	private PaymentDTO entityToDTO(Payment payment) {

		return PaymentDTO.builder().paymentNo(payment.getPaymentNo()).bookingNo(payment.getBooking().getBookingNo())
				.paymentId(payment.getPaymentId()).storeId(payment.getStoreId()).channelKey(payment.getChannelKey())
				.orderName(payment.getOrderName()).paymentAmount(payment.getPaymentAmount())
				.currency(payment.getCurrency()).payMethod(payment.getPayMethod().name())
				.pgProvider(payment.getPgProvider()).paymentStatus(payment.getPaymentStatus().name())
				.approvedAt(payment.getApprovedAt()).canceledAt(payment.getCanceledAt())
				.refundAmount(payment.getRefundAmount()).failReason(payment.getFailReason())
				.rawResponse(payment.getRawResponse()).build();
	}
}
