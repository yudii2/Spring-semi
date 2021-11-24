package com.kh.spring.mountain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.spring.mountain.model.repository.MountainRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})	
public class MountainTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MountainRepository mountainRepository;
	
	@Test
	public void selectAllMountain() {
		mountainRepository.selectAllMountain().forEach(e -> logger.debug(e.toString()));
	}
}
