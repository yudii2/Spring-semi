package com.kh.spring.mountain.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.common.util.PageDTO;
import com.kh.spring.mountain.model.dto.Mountain;
import com.kh.spring.mountain.model.service.MountainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("mountain")
public class MountainController {
	
	private final MountainService mountainService;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("search-seoul")
	public void searchSeoul(Model model
							,@RequestParam(value = "p", required = false) String currPage) {
		int mountainCnt = mountainService.countSeoulMountain();
		int cntPerPage = 9;
		
		if(currPage == null){
			currPage = "1";
		}
		PageDTO page = new PageDTO(mountainCnt, Integer.parseInt(currPage), cntPerPage);
		logger.debug(page.toString());
		
		model.addAttribute("page", page)
			.addAttribute("seoulMountain",mountainService.selectSeoulMountainByPage(page));
	}
	
	@GetMapping("search-gyeonggi")
	public void searchGyeonggi(Model model
								,@RequestParam(value = "p", required = false) String currPage) {
		int mountainCnt = mountainService.countGyeonggiMountain();
		int cntPerPage = 9;
		
		if(currPage == null){
		currPage = "1";
		}
		PageDTO page = new PageDTO(mountainCnt, Integer.parseInt(currPage), cntPerPage);
		logger.debug(page.toString());
		
		model.addAttribute("page", page)
		.addAttribute("gyeonggiMountain",mountainService.selectGyeonggiMountainByPage(page));
	}
	
	@GetMapping("detail/{mName}")
	public String detail(@PathVariable String mName, Model model) {
		
		model.addAttribute("mountainInfo", mountainService.selectMountainByName(mName));
		return "mountain/mountain-detail";
	}
	@GetMapping("detail")
	public String searchMountain(String mName, Model model) {
		
		model.addAttribute("mountainInfo", mountainService.selectMountainByName(mName));
		return "mountain/mountain-detail";
	}
}
