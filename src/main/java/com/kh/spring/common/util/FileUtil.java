package com.kh.spring.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.common.code.Config;
import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandleableException;

public class FileUtil {
	
	public FileDTO fileUpload(MultipartFile mf) {
		FileDTO file = createFileDTO(mf);
		
		try {
			mf.transferTo(new File(getSavePath() + file.getRenameFileName()));
		} catch (IllegalStateException | IOException e) {
			throw new HandleableException(ErrorCode.FAILED_FILE_UPLOAD_ERROR,e);	//우리만의 예외처리 + 예외 서비스단으로 넘김
		}
		
		return file;
	}
	
	//경로 설정 메서드
	private String getSavePath() {
		
		//	2. 저장경로를 웹어플리케이션 외부로 지정
		//	   저장경로를 "외부경로 + /연/월/일" 형태로 작성
		String subPath = getSubPath();
		String savePath = Config.FILE_UPLOAD_PATH.DESC + subPath;
		
		File dir = new File(savePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		return savePath;
	}
	
	private String getSubPath() {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int date = today.get(Calendar.DATE);
		
		return year + "/" + month + "/" + date + "/";

	}
	
	//세미에 파일을 외부에 저장하기 위해 톰캣서버 설정을 건드렸다. PreResourse속성을 이용해 /file로 들어온 요청에 대해서는 /upload폴더에 저장하도록 지정했다.
	private FileDTO createFileDTO(MultipartFile mf) {
		FileDTO fileDTO = new FileDTO();
		String originFileName = mf.getOriginalFilename();
		String renameFileName = UUID.randomUUID().toString();
		if(originFileName.contains(".")) {
			renameFileName = renameFileName += originFileName.substring(originFileName.lastIndexOf("."));	//.포함해서 뒤까지 파싱	(확장자 구분 위함)
		}
		String savePath = getSubPath();
		
		fileDTO.setOriginFileName(originFileName);
		fileDTO.setRenameFileName(renameFileName);
		fileDTO.setSavePath(savePath);
		return fileDTO;
	}
}
