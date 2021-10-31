package com.kh.spring.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.repository.BoardRepository;
import com.kh.spring.common.util.FileDTO;
import com.kh.spring.common.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	
	public void insertBoard(List<MultipartFile> mfs, Board board) {
	
		FileUtil fileUtil = new FileUtil();
		boardRepository.insertBoard(board);
		
		for (MultipartFile multipartFile : mfs) {
			if(!multipartFile.isEmpty()) {
				boardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));				
			}
		}
	}
	
	@Override
	public Map<String, Object> selectBoardDetail(String bdIdx){
		
		Board board = boardRepository.selectBoardDetail(bdIdx);		
		List<FileDTO> files = boardRepository.selectFilesByBdIdx(bdIdx);

		return Map.of("board", board, "files", files);
	}

}
