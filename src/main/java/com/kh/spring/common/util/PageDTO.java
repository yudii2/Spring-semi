package com.kh.spring.common.util;

import lombok.Data;

@Data
public class PageDTO {
	
	private int currPage;
	private int startNum;	//페이지 넘버링
	private int endNum;	//페이지 넘버링
	private int totalCnt;	//총 게시물
	private int cntPerPage;
	private int lastPage;
	private int start;		//sql쿼리용	
	private int end; 		//sql쿼리용	
	private int pageCnt = 5;	//페이지 넘버링 1,2,3,4,5 >> 6,7,8,9,10
	
	public PageDTO(int totalCnt,int currPage, int cntPerPage) {
		this.currPage = currPage;
		this.totalCnt = totalCnt;
		this.cntPerPage = cntPerPage;
		calcLastPage(totalCnt, cntPerPage);
		calcStartEndPage(currPage, pageCnt);
		calcStartEnd(currPage, cntPerPage);
	}	
	
	public void calcLastPage(int totalCnt, int cntPerPage) {
		setLastPage((int)(Math.ceil(totalCnt/cntPerPage)) == 0 ? 1 : (int)(Math.ceil(totalCnt/cntPerPage)));	//왜 +1이 필요하지?
	}
	
	public void calcStartEndPage(int currPage, int pageCnt) {
		setStartNum(currPage - (currPage-1)%pageCnt);	//1,6,11
		if(getStartNum() < 1) {
			setStartNum(1);
		}
		setEndNum(getStartNum() + 4);	//5,10,15
		if(getLastPage() < getEndNum()) {					//페이지 넘버링숫자가 6인 경우(curr 6/pageCnt 5 * 5)
			setEndNum(getLastPage());
		}
	}
	
	public void calcStartEnd(int currPage, int cntPerPage) {
		setEnd(currPage * cntPerPage);
		setStart(getEnd() - cntPerPage + 1);
	}


}
