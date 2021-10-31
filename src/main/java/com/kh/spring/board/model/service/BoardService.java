package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;

public interface BoardService {	//전자정부 프레임워크에서 interface를 사용하는 것이 다수 관습으로 남아있음

	public void insertBoard(List<MultipartFile> mfs, Board board);

	public Map<String, Object> selectBoardDetail(String bdIdx);
	
}
