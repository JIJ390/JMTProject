package edu.kh.jmt.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.jmt.board.dto.Comment;
import edu.kh.jmt.board.service.CommentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;
	
	
	/** 댓글창 비동기
	 * @param boardNo
	 * @param model
	 * @return
	 */
	@GetMapping("boardComment")
	public String boardComment(
			@RequestParam("boardNo") int boardNo,
			Model model) {
		
		List<Comment> commentList 
		= service.selectCommentList(boardNo);
		
		
		model.addAttribute("commentList", commentList);
		
		return "/board/comment";
	}
	
	
	
	
	
	
	
	
	
}


