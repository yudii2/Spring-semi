package com.kh.spring.mountain.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kh.spring.common.util.PageDTO;
import com.kh.spring.mountain.model.dto.Mountain;
import com.kh.spring.mountain.model.repository.MountainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MountainServiceImpl implements MountainService{
	
	private final MountainRepository mountainRepository;

	@Override
	public List<Mountain> selectSeoulMountain() {
		Optional<List<Mountain>> mountains = Optional.of(mountainRepository.selectAllMountain());
		return mountains.orElse(List.of());
	}

	@Override
	public int countSeoulMountain() {
		return mountainRepository.countSeoulMountain();
	}

	@Override
	public List<Mountain> selectSeoulMountainByPage(PageDTO page) {
		Optional<List<Mountain>> mountains = Optional.of(mountainRepository.selectSeoulMountainByPage(page));
		return mountains.orElse(List.of());
	}

	@Override
	public int countGyeonggiMountain() {
		return mountainRepository.countGyeonggiMountain();
	}

	@Override
	public List<Mountain> selectGyeonggiMountainByPage(PageDTO page) {
		Optional<List<Mountain>> mountains = Optional.of(mountainRepository.selectGyeonggiMountainByPage(page));
		return mountains.orElse(List.of());
	}

	@Override
	public Mountain selectMountainByName(String mName) {
		return mountainRepository.selectMountainByName(mName);
	}

}
