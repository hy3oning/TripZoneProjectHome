package com.kh.trip.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.trip.domain.HostProfile;
import com.kh.trip.domain.Lodging;
import com.kh.trip.domain.enums.LodgingStatus;
import com.kh.trip.dto.LodgingDTO;
import com.kh.trip.repository.HostProfileRepository;
import com.kh.trip.repository.LodgingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LodgingServiceImpl implements LodgingService {
	private final LodgingRepository lodgingRepository;
	private final HostProfileRepository hostProfileRepository;

	@Override
	public List<LodgingDTO> getList() {

		List<Lodging> lodgingList = lodgingRepository.findByStatus(LodgingStatus.ACTIVE);

		return lodgingList.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public List<LodgingDTO> getInactiveList() {
		List<Lodging> lodgingList = lodgingRepository.findByStatus(LodgingStatus.INACTIVE);

		return lodgingList.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public Long register(LodgingDTO lodgingDTO) {
		boolean exists = lodgingRepository.existsByHostProfile_HostNoAndLodgingNameAndAddress(lodgingDTO.getHostNo(),
				lodgingDTO.getLodgingName(), lodgingDTO.getAddress());

		if (exists) {
			throw new IllegalArgumentException("이미 등록된 숙소입니다.");
		}
		Lodging lodging = dtoToEntity(lodgingDTO);
		Lodging result = lodgingRepository.save(lodging);

		return result.getLodgingNo();
	}

	@Override
	public LodgingDTO getDetail(Long lodgingNo) {

		Optional<Lodging> result = lodgingRepository.findById(lodgingNo);
		Lodging lodging = result
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 숙소입니다. lodgingNo=" + lodgingNo));

		return entityToDTO(lodging);
	}

	@Override
	public void modify(LodgingDTO lodgingDTO) {

		Optional<Lodging> result = lodgingRepository.findById(lodgingDTO.getLodgingNo());
		Lodging lodging = result.orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 숙소입니다. lodgingNo=" + lodgingDTO.getLodgingNo()));

		lodging.changeName(lodgingDTO.getLodgingName());
		lodging.changeType(lodgingDTO.getLodgingType());
		lodging.changeRegion(lodgingDTO.getRegion());
		lodging.changeAddress(lodgingDTO.getAddress(), lodgingDTO.getDetailAddress(), lodgingDTO.getZipCode());
		lodging.changeLocation(lodgingDTO.getLatitude(), lodgingDTO.getLongitude());
		lodging.changeDescription(lodgingDTO.getDescription());
		lodging.changeCheckTime(lodgingDTO.getCheckInTime(), lodgingDTO.getCheckOutTime());

		lodgingRepository.save(lodging);
	}

	@Override
	public void remove(Long lodgingNo) {
		Optional<Lodging> result = lodgingRepository.findById(lodgingNo);
		Lodging lodging = result
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 숙소입니다. lodgingNo = " + lodgingNo));
		lodging.changeStatus(LodgingStatus.INACTIVE);
		lodgingRepository.save(lodging);
	}

	@Override
	public void restore(Long lodgingNo) {
		Optional<Lodging> result = lodgingRepository.findById(lodgingNo);
		Lodging lodging = result
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 숙소입니다. lodgingNo = " + lodgingNo));

		lodging.changeStatus(LodgingStatus.ACTIVE);
		lodgingRepository.save(lodging);
	}

	private Lodging dtoToEntity(LodgingDTO lodgingDTO) {

		HostProfile hostProfile = hostProfileRepository.findById(lodgingDTO.getHostNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 호스트입니다. hostNo=" + lodgingDTO.getHostNo()));

		return Lodging.builder().hostProfile(hostProfile).lodgingName(lodgingDTO.getLodgingName())
				.lodgingType(lodgingDTO.getLodgingType()).region(lodgingDTO.getRegion())
				.address(lodgingDTO.getAddress()).detailAddress(lodgingDTO.getDetailAddress())
				.zipCode(lodgingDTO.getZipCode()).latitude(lodgingDTO.getLatitude())
				.longitude(lodgingDTO.getLongitude()).description(lodgingDTO.getDescription())
				.checkInTime(lodgingDTO.getCheckInTime()).checkOutTime(lodgingDTO.getCheckOutTime()).build();
	}

	private LodgingDTO entityToDTO(Lodging lodging) {

		return LodgingDTO.builder().lodgingNo(lodging.getLodgingNo()).hostNo(lodging.getHostProfile().getHostNo())
				.lodgingName(lodging.getLodgingName()).lodgingType(lodging.getLodgingType()).region(lodging.getRegion())
				.address(lodging.getAddress()).detailAddress(lodging.getDetailAddress()).zipCode(lodging.getZipCode())
				.latitude(lodging.getLatitude()).longitude(lodging.getLongitude()).description(lodging.getDescription())
				.checkInTime(lodging.getCheckInTime()).checkOutTime(lodging.getCheckOutTime())
				.status(lodging.getStatus().name()).build();
	}

}
