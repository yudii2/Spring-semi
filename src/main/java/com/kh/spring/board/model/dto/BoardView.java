package com.kh.spring.board.model.dto;

import lombok.Data;

public class BoardView extends Board{

	private int replyCnt;

	public int getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}

	@Override
	public String toString() {
		return super.toString() + "BoardView [replyCnt=" + replyCnt + "]";
	}
	
}
