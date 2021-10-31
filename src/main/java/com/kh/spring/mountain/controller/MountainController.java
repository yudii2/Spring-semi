package com.kh.spring.mountain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.mountain.model.service.MountainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("mountain")
public class MountainController {
	
	private final MountainService mountainService;
	
	@GetMapping("search-seoul")
	public void searchSeoul() {}
	
	@GetMapping("search-gyeonggi")
	public void searchGyeonggi() {}
	
	@GetMapping("detail/{mName}")
	public void detial() {
		
	}

}
