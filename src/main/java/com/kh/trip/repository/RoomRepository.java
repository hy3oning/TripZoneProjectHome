package com.kh.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Room;
import com.kh.trip.domain.enums.RoomStatus;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findByLodging_LodgingNoAndStatus(Long lodgingNo, RoomStatus status);

	boolean existsByLodging_LodgingNoAndRoomName(Long lodgingNo, String roomName);
}
