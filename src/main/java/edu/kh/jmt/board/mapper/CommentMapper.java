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

	/** 댓글등록
	 * @param comment
	 * @return
	 */
	int commentInsert(Comment comment);

	/**
	 * 댓삭
	 * @param commentNo
	 * @return
	 */
	int commentDelete(int commentNo);

	/** 댓수
	 * @param comment
	 * @return
	 */
	int commentUpdate(Comment comment);

	
	
}
