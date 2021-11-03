package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.dto.BoardView;
import com.kh.spring.board.model.dto.Reply;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.schedule.model.dto.Schedule;

public interface BoardService {	//전자정부 프레임워크에서 interface를 사용하는 것이 다수 관습으로 남아있음

	public void insertBoard(List<MultipartFile> mfs, Board board);

	public Map<String, Object> selectBoardDetail(String bdIdx);

	public List<BoardView> selectAllBoard();
	
	int countBoard();	
	List<BoardView> selectBoardByPage(PageDTO pageDto);

	int countMyPost(Member member);
	List<Board> selectMyPost(Member member, PageDTO pageDto);

	public int countMyReply(Member member);

	List<Reply> selectMyReply(Member member, PageDTO pageDto);

	List<Schedule> selectMySchedule(Member member, PageDTO pageDto);


	
}
