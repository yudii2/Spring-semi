package com.kh.spring.board.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.dto.BoardView;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("board")
public class BoardController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@GetMapping("board-form")
	public void boardForm() {}
	
	@GetMapping("board")
	public void board( Model model
						,@RequestParam(value = "p", required = false) String currPage) {
		
		int bdCnt = boardService.countBoard();
		int cntPerPage = 10;
		
		if(currPage == null) {
			currPage = "1";
		}
		
		PageDTO pageDto = new PageDTO(bdCnt, Integer.parseInt(currPage), cntPerPage);
		
		logger.debug(pageDto.toString());
		model.addAttribute("page", pageDto)
			.addAttribute("boardList",boardService.selectBoardByPage(pageDto));
		
	}
	
	@PostMapping("upload")
	public String uploadBoard(Board board
								,List<MultipartFile> files
								,@SessionAttribute("authentication") Member member) {	//스프링은 ModelAttribute를 사용해서 알아서 multiPartParam객체를 파싱해 dto객체에 담아준다
		
		board.setUserId(member.getUserId());
		board.setNickname(member.getNickname());
		boardService.insertBoard(files, board);
			
		return "redirect:/";
	}
	
	@GetMapping("board-detail")
	public void boardDetail(String bdIdx, Model model) {	//parameter로 넘어온 값을 string으로 받고 있다.

		Map<String,Object> commandMap = boardService.selectBoardDetail(bdIdx);
		model.addAttribute("datas", commandMap);
		
	}
	
	
}
