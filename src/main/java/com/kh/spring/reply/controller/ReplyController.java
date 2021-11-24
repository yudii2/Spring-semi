package com.kh.spring.reply.controller;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.dto.Reply;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.reply.model.service.ReplyService;

@Controller
@RequestMapping("reply")
public class ReplyController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ReplyService replyService;
	
	public ReplyController(ReplyService replyService) {
		super();
		this.replyService = replyService;
	}



	@GetMapping("add-reply")
	public String addReply(@Param(value = "bdIdx") String bdIdx
			,Reply reply, @SessionAttribute(name = "authentication") Member member) {
		
		logger.debug(bdIdx);
		
		Reply replyObj = new Reply();
		replyObj.setBdIdx(bdIdx);
		replyObj.setUserId(member.getUserId());
		replyObj.setContent(reply.getContent());
		replyObj.setNickname(member.getNickname());
		
		replyService.insertReply(replyObj);
		
		return "board/board-detail";
	}
}
