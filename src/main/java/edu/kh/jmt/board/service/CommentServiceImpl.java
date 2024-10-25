package edu.kh.jmt.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.board.dto.Comment;
import edu.kh.jmt.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentMapper mapper;
	
	// 댓글창비동기
	@Override
	public List<Comment> selectCommentList(int boardNo) {
		return mapper.selectCommentList(boardNo);
	}
	
	
	
}
