package com.kh.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
