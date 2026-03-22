package com.kh.trip.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.trip.domain.Lodging;
import com.kh.trip.domain.Room;
import com.kh.trip.domain.enums.LodgingStatus;
import com.kh.trip.domain.enums.RoomStatus;
import com.kh.trip.dto.RoomDTO;
import com.kh.trip.repository.LodgingRepository;
import com.kh.trip.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;
	private final LodgingRepository lodgingRepository;

	@Override
	public Long register(RoomDTO roomDTO) {
		Room room = dtoToEntity(roomDTO);
		Room result = roomRepository.save(room);
		return result.getRoomNo();
	}

	@Override
	public List<RoomDTO> getListByLodging(Long lodgingNo) {
		List<Room> roomList = roomRepository.findByLodging_LodgingNoAndStatus(lodgingNo, RoomStatus.AVAILABLE);
		return roomList.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public RoomDTO getDetail(Long roomNo) {
		Room room = roomRepository.findById(roomNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실입니다. roomNo=" + roomNo));

		return entityToDTO(room);
	}

	@Override
	public void modify(RoomDTO roomDTO) {
		Room room = roomRepository.findById(roomDTO.getRoomNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실입니다. roomNo =" + roomDTO.getRoomNo()));
		room.changeName(roomDTO.getRoomName());
		room.changeType(roomDTO.getRoomType());
		room.changeMaxGuestCount(roomDTO.getMaxGuestCount());
		room.changePricePerNight(roomDTO.getPricePerNight());
		room.changeRoomCount(roomDTO.getRoomCount());
		room.changeDescription(roomDTO.getDescription());

		roomRepository.save(room);
	}

	@Override
	public void remove(Long roomNo) {

		Room room = roomRepository.findById(roomNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실입니다. roomNo=" + roomNo));

		room.changeStatus(RoomStatus.UNAVAILABLE);

		roomRepository.save(room);
	}

	@Override
	public void restore(Long roomNo) {

		Room room = roomRepository.findById(roomNo)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실입니다. roomNo=" + roomNo));

		room.changeStatus(RoomStatus.AVAILABLE);

		roomRepository.save(room);
	}

	private Room dtoToEntity(RoomDTO roomDTO) {

		Lodging lodging = lodgingRepository.findById(roomDTO.getLodgingNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 숙소입니다. lodgingNo=" + roomDTO.getLodgingNo()));

		if (lodging.getStatus() != LodgingStatus.ACTIVE) {
			throw new IllegalArgumentException("비활성 숙소에는 객실을 등록할 수 없습니다.");
		}

		boolean exists = roomRepository.existsByLodging_LodgingNoAndRoomName(roomDTO.getLodgingNo(),
				roomDTO.getRoomName());

		if (exists) {
			throw new IllegalArgumentException("이미 등록된 객실명입니다.");
		}

		return Room.builder().lodging(lodging).roomName(roomDTO.getRoomName()).roomType(roomDTO.getRoomType())
				.maxGuestCount(roomDTO.getMaxGuestCount()).pricePerNight(roomDTO.getPricePerNight())
				.roomCount(roomDTO.getRoomCount()).description(roomDTO.getDescription()).build();
	}

	private RoomDTO entityToDTO(Room room) {

		return RoomDTO.builder().roomNo(room.getRoomNo()).lodgingNo(room.getLodging().getLodgingNo())
				.roomName(room.getRoomName()).roomType(room.getRoomType()).maxGuestCount(room.getMaxGuestCount())
				.pricePerNight(room.getPricePerNight()).roomCount(room.getRoomCount())
				.description(room.getDescription()).status(room.getStatus().name()).build();
	}

}
