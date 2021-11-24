package com.kh.spring.mountain.model.service;

import java.util.Collection;
import java.util.List;

import com.kh.spring.common.util.PageDTO;
import com.kh.spring.mountain.model.dto.Mountain;

public interface MountainService {

	List<Mountain> selectSeoulMountain();

	int countSeoulMountain();

	List<Mountain> selectSeoulMountainByPage(PageDTO pageDto);

	int countGyeonggiMountain();

	List<Mountain> selectGyeonggiMountainByPage(PageDTO page);

	Mountain selectMountainByName(String mName);

}
