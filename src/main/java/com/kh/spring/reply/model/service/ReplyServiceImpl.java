package com.kh.spring.reply.model.service;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dto.Reply;
import com.kh.spring.reply.model.repository.ReplyRepository;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	private final ReplyRepository replyRepository;

	public ReplyServiceImpl(ReplyRepository replyRepository) {
		super();
		this.replyRepository = replyRepository;
	}

	@Override
	public void insertReply(Reply replyObj) {
		replyRepository.insertReply(replyObj);
	}

}
