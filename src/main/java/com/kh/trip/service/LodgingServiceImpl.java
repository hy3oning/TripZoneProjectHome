package com.kh.trip.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.trip.domain.HostProfile;
import com.kh.trip.domain.Lodging;
import com.kh.trip.dto.LodgingDTO;
import com.kh.trip.repository.HostProfileRepository;
import com.kh.trip.repository.LodgingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LodgingServiceImpl implements LodgingService {
	private final LodgingRepository lodgingRepository;
	private final HostProfileRepository hostProfileRepository;

	@Override
	public List<LodgingDTO> getList() {

		List<Lodging> lodgingList = lodgingRepository.findAll();
		return lodgingList.stream()
				.map(lodging -> LodgingDTO.builder().lodgingNo(lodging.getLodgingNo())
						.hostNo(lodging.getHostProfile().getHostNo()).lodgingName(lodging.getLodgingName())
						.lodgingType(lodging.getLodgingType()).region(lodging.getRegion()).address(lodging.getAddress())
						.detailAddress(lodging.getDetailAddress()).zipCode(lodging.getZipCode())
						.latitude(lodging.getLatitude()).longitude(lodging.getLongitude())
						.description(lodging.getDescription()).checkInTime(lodging.getCheckInTime())
						.checkOutTime(lodging.getCheckOutTime()).status(lodging.getStatus().name()).build())
				.toList();
	}

	@Override
	public Long register(LodgingDTO lodgingDTO) {
		HostProfile hostProfile = hostProfileRepository.findById(lodgingDTO.getHostNo())
			    .orElseThrow(() -> new IllegalArgumentException(
			        "존재하지 않는 호스트입니다. hostNo=" + lodgingDTO.getHostNo()));

		Lodging lodging = Lodging.builder().hostProfile(hostProfile).lodgingName(lodgingDTO.getLodgingName())
				.lodgingType(lodgingDTO.getLodgingType()).region(lodgingDTO.getRegion())
				.address(lodgingDTO.getAddress()).detailAddress(lodgingDTO.getDetailAddress())
				.zipCode(lodgingDTO.getZipCode()).latitude(lodgingDTO.getLatitude())
				.longitude(lodgingDTO.getLongitude()).description(lodgingDTO.getDescription())
				.checkInTime(lodgingDTO.getCheckInTime()).checkOutTime(lodgingDTO.getCheckOutTime()).build();

		Lodging savedLodging = lodgingRepository.save(lodging);
		return savedLodging.getLodgingNo();
	}

}
