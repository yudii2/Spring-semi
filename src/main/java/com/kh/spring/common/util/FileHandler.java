package com.kh.spring.common.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.spring.common.code.Config;

@Controller
public class FileHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("download")
	public ResponseEntity<UrlResource> downloadFile(FileDTO file) throws MalformedURLException, UnsupportedEncodingException{	//ResponseEntity 왜 필요? 파일다운로드를 위해서는 우리가 요청헤더를 작성해야 하기 때문에!
		UrlResource resource = new UrlResource(Config.DOMAIN.DESC + file.getDownloadURL());
		
		logger.debug(file.getDownloadURL());
		
		String originFileName = URLEncoder.encode(file.getOriginFileName(),"UTF-8");
		
		ResponseEntity<UrlResource> response = 
				ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + originFileName)
				.body(resource);
		return response;
	}
}
