package edu.kh.jmt.board.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.board.dto.Board;
import edu.kh.jmt.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
@Slf4j
public class BoardController {

	private final BoardService service;
	
	/**
	 * 게시글 조회
	 * @param model
	 * @return
	 */
	@GetMapping("boardMain")
	public String boardMain(
			Model model) {
		
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
		
		System.out.println(inputBoard);
		
		// 1) 작성자 회원번호를 inputBoard에 세팅
		//		inputBoard.setMemberNo(loginMember.getMemberNo());
		inputBoard.setMemberNo(1);
		
		// 2) 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		int boardNo = service.boardWrite(inputBoard, boardImage);
		
		
		// 3) 서비스 호출 후 결과(작성된 게시글 번호) 반환받기
		
		
		return "redirect:boardMain";
	}
	
	/** 게시글 삭제
	 * @param boardNo
	 * @param loginMember
	 * @param ra
	 * @param referer : 현재 컨트롤러 메서드를 요청한 페이지 주소(이전페이지주소 == main페이지)
	 * @return
	 */
	@PostMapping("delete")
	public String boardDelete(
			@RequestParam("boardNo")int boardNo,
//			@SessionAttribute("loginMember")Member loginMember,
			RedirectAttributes ra,
			@RequestHeader("referer") String referer) {
		
		log.debug("referer : {}", referer);
		
		// 행의 개수때문에 int를 적어줘야함
		int delete = service.boardDelete(boardNo, 1);
		
		String path = null;
		String message = null;
		
		
		
		if(delete == 0) {
			path = "referer";
			message = "삭제실패!";
		}else {
			path = "boardMain";
			message = "삭제되었습니다!";
		}

		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}

	/** 게시글 수정화면 전환
	 * @param boardNo : boardNo
	 * @param loginMember : 로그인한 회원정보
	 * @param ra : 리다이렉트시 request scope로 데이터 전달
	 * @param model : forward 시 request scope로 데이터 전달
	 * @return
	 */
	@PostMapping("updateView")
	public String updateView(
			@RequestParam("boardNo") int boardNo,
//			@SessionAttribute("loginMember")Member loginMember,
			RedirectAttributes ra,
			Model model){
			
		Board board = service.updateView(boardNo);
		
		// Model -> forward시 "board", board K : V 로 전달하기!!!
		model.addAttribute("board", board);
		
		return "board/boardUpdate";

	}
	
//	@SessionAttribute("loginMember")Member loginMember,
	
	@PostMapping("update")
	public String boardUpdate(
			@RequestParam("boardImage")MultipartFile boardImage,
			@ModelAttribute Board inputBoard,
			RedirectAttributes ra
			) {
		
//		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		
		int result = service.boardUpdate(inputBoard, boardImage);
		
		String message = null;
		
		if(result > 0) {
			message = "게시글이 수정되었습니다";
		}else {
			message = "수정 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:boardMain";
	}
	
	
	@GetMapping("boardComment")
	public String boardComment() {
		
		return "/board/boardComment";
	}
	
	/** 게시글 수정화면 전환
	 * @param boardNo
	 * @param ra
	 * @param model
	 * @return
	 */
	
	
	
	
	
	
	
}
