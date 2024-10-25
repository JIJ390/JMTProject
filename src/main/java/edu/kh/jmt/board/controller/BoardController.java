package edu.kh.jmt.board.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.board.dto.Board;
import edu.kh.jmt.board.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
	@GetMapping("boardMain")
	public String boardMain(
			Model model
			) {
		
		List<Board> boardList = service.boardMain();
				
				
		model.addAttribute("boardList", boardList);
		
		
		
		return "/board/boardMain";
	}
	

	@GetMapping("boardWrite")
	public String boardWrite() {
		
		return "/board/boardWrite";
	}

	/** 게시글 등록
	 * @param inputBoard : 제출된 key값이 일치하는 필드의 값이 저장된 객체(커맨드객체)
	 * @param loginMember : 로그인한 회원정보(글쓴이 회원번호 필요)
	 * @param images : 제출된 file 타입 input 태그 데이터
	 * @param ra : 리다이렉트시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("writeToMain")
	public String boardWrite(
			@ModelAttribute Board inputBoard,
//			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("boardImage") MultipartFile boardImage,
			RedirectAttributes ra
			) {
		
		
		// 1) 작성자 회원번호를 inputBoard에 세팅
		//		inputBoard.setMemberNo(loginMember.getMemberNo());
		inputBoard.setMemberNo(1);
		
		// 2) 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		int boardNo = service.boardWrite(inputBoard, boardImage);
		
		
		// 3) 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		
		
		return "redirect:boardMain";
	}
	
	@GetMapping("boardComment")
	public String boardComment() {
		
		return "/board/boardComment";
		
		
	}
	
}
