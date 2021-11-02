package com.kh.spring.board.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.dto.BoardView;
import com.kh.spring.common.util.FileDTO;
import com.kh.spring.common.util.PageDTO;
import com.kh.spring.member.model.dto.Member;

@Mapper
public interface BoardRepository {

	@Insert("insert into board(bd_idx,user_id,title,content) "
			+ "values(sc_board_idx.nextval, #{userId}, #{title}, #{content})")
	void insertBoard(Board board);
	
	@Insert("insert into file_info(fl_idx,type_idx,origin_file_name,rename_file_name,save_path) "
			+ "values(sc_file_idx.nextval,sc_board_idx.currval, #{originFileName}, #{renameFileName}, #{savePath})")
	void insertFileInfo(FileDTO fileDTO);

	@Select("select * from board where bd_idx = #{bdIdx}")
	Board selectBoardDetail(String bdIdx);

	@Select("select * from file_info where type_idx = #{bdIdx}")
	List<FileDTO> selectFilesByBdIdx(String bdIdx);

	@Select("select * from board_view")
	List<BoardView> selectAllBoard();
	
	@Select("select count(*) from board")
	int countBoard();
	
	List<BoardView> selectBoardByPage(PageDTO pageDto);

	@Select("select count(*) from board where user_id = #{userId}")
	int countMyPost(Member member);

	List<Board> selectMyPost(@Param("writer") Member member,@Param("writer") PageDTO pageDto);
}
