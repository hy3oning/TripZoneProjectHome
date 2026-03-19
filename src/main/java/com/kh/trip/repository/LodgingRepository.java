package com.kh.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Lodging;

public interface LodgingRepository extends JpaRepository<Lodging, Long> {

}
