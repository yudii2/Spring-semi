package com.kh.spring.mountain.model.service;

import org.springframework.stereotype.Service;

import com.kh.spring.mountain.model.repository.MountainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MountainServiceImpl implements MountainService{
	
	private final MountainRepository mountainRepository;

}
