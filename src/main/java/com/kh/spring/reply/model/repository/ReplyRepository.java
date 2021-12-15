package com.kh.spring.reply.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.board.model.dto.Reply;

@Mapper
public interface ReplyRepository {

	@Insert("insert into reply (rp_idx,bd_idx,user_id,content,nickname) values(sc_rp_idx.nextval,#{bdIdx},#{userId},#{content},#{nickname})")
	void insertReply(Reply replyObj);

}
