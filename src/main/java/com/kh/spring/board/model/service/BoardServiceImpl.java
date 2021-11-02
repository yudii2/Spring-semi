package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.dto.BoardView;
import com.kh.spring.board.model.repository.BoardRepository;
import com.kh.spring.common.util.FileDTO;
import com.kh.spring.common.util.FileUtil;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
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

	@Override
	public List<BoardView> selectAllBoard() {
		return boardRepository.selectAllBoard();
	}

	@Override
	public int countBoard() {
		return boardRepository.countBoard();
	}

	@Override
	public List<BoardView> selectBoardByPage(PageDTO pageDto) {
		return boardRepository.selectBoardByPage(pageDto);
	}

	@Override
	public int countMyPost(Member member) {
		return boardRepository.countMyPost(member);
	}

	@Override
	public List<Board> selectMyPost(Member member, PageDTO pageDto) {
		return boardRepository.selectMyPost( member,pageDto);
	}

}
