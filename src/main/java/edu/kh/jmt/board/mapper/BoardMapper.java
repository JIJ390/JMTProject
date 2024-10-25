package edu.kh.jmt.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.board.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시글 등록
	 * @param inputBoard
	 * @return
	 */
	int boardWrite(Board inputBoard);

	List<Board> boardMain();

}
