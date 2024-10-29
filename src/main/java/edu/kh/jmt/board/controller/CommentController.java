package edu.kh.jmt.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.jmt.board.dto.Comment;
import edu.kh.jmt.board.service.CommentService;
import edu.kh.jmt.myPage.dto.Member;
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

//	
	
	/**
	 *  댓글 등록
	 */
	@ResponseBody
	@PostMapping("boardComment")
	public int commentInsert(
			@RequestBody Comment comment,
			@SessionAttribute("loginMember")Member loginMember) {
		
		
//		comment.setMemberNo(loginMember.getMemberNo());
		
		comment.setMemberNo(loginMember.getMemberNo());
		
		System.out.println(comment);
		System.out.println(comment);
		System.out.println(comment);
		System.out.println(comment);
		 
		return service.commentInsert(comment);
		
	}
	
	/**
	 * 댓글 삭제
	 */
	@ResponseBody
	@DeleteMapping("boardComment")
	public int commentDelete(
			@RequestBody int commentNo) {
		
		
		return service.commentDelete(commentNo);
	}

	/** 댓글 수정
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@PutMapping("boardComment")
	public int commentUpdate(
			@RequestBody Comment comment) {
		
   // 항상 넘기기전에 syso 찍어보기
		
		return service.commentUpdate(comment);
		
	}
	
	
	
	
}


