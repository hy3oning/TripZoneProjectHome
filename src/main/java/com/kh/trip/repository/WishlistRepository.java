package com.kh.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.trip.domain.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
