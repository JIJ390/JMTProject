package edu.kh.jmt.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.board.dto.Comment;

@Mapper
public interface CommentMapper {

	/**
	 *  댓글창비동기
	 * @param boardNo
	 * @return
	 */
	List<Comment> selectCommentList(int boardNo);

	
	
}
