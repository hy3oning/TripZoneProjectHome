package com.kh.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Lodging;
import com.kh.trip.domain.enums.LodgingStatus;

public interface LodgingRepository extends JpaRepository<Lodging, Long> {

	List<Lodging> findByStatus(LodgingStatus status);
	
	boolean existsByHostProfile_HostNoAndLodgingNameAndAddress(
			Long hostNo,
			String lodgingName,
			String address
	);
}
