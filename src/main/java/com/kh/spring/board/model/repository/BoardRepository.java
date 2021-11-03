package com.kh.spring.board.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.dto.BoardView;
import com.kh.spring.board.model.dto.Reply;
import com.kh.spring.common.util.FileDTO;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.schedule.model.dto.Schedule;

@Mapper
public interface BoardRepository {

	@Insert("insert into board(bd_idx,user_id,subject,title,content,nickname) "
			+ "values(sc_bd_idx.nextval, #{userId},#{subject}, #{title}, #{content}, #{nickname})")
	void insertBoard(Board board);
	
	@Insert("insert into file_info(fl_idx,type_idx,origin_file_name,rename_file_name,save_path) "
			+ "values(sc_fl_idx.nextval,sc_bd_idx.currval, #{originFileName}, #{renameFileName}, #{savePath})")
	void insertFileInfo(FileDTO fileDTO);

	@Select("select * from board where bd_idx = #{bdIdx}")
	Board selectBoardDetail(String bdIdx);

	@Select("select * from file_info where type_idx = #{bdIdx}")
	List<FileDTO> selectFilesByBdIdx(String bdIdx);

	@Select("select * from board_view")
	List<BoardView> selectAllBoard();
	
	@Select("select count(*) from board")
	int countBoard();
	
	List<BoardView> selectBoardByPage(@Param("page") PageDTO pageDto);

	@Select("select count(*) from board where user_id = #{userId}")
	int countMyPost(Member member);

	List<Board> selectMyPost(@Param("writer") Member member,@Param("page") PageDTO pageDto);

	@Select("select count(*) from reply where user_id = #{userId}")
	int countMyReply(Member member);

	List<Reply> selectMyReply(@Param("writer") Member member,@Param("page") PageDTO pageDto);

	List<Schedule> selectMySchedule(@Param("parti") Member member,@Param("page") PageDTO pageDto);

	@Select("select m_height from mountain where m_name = #{mountainName}")
	String selectMountainHeight(String mountainName);
	
}
