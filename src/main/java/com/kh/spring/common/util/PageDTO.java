package com.kh.spring.common.util;

import lombok.Data;

@Data
public class PageDTO {
	
	private int currPage;
	private int startPage;
	private int endPage;
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
		setLastPage((int)Math.ceil(totalCnt/cntPerPage));
	}
	
	public void calcStartEndPage(int currPage, int pageCnt) {
		setEndPage((int)Math.ceil(currPage/pageCnt * pageCnt));
		if(getLastPage() < getEndPage()) {					//페이지 넘버링숫자가 6인 경우(curr 6/pageCnt 5 * 5)
			setEndPage(getLastPage());
		}
		setStartPage(getEndPage() - pageCnt + 1);			//????
		if(getStartPage() < 1) {
			setStartPage(1);
		}
	}
	
	public void calcStartEnd(int currPage, int cntPerPage) {
		setEnd(currPage * cntPerPage);
		setStart(getEnd() - cntPerPage + 1);
	}


}
