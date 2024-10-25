package edu.kh.jmt.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.board.dto.Board;

public interface BoardService {

	/** 게시글 등록
	 * @param inputBoard : 제출된 key 값이 일치하는 필드의 값이 저장된 객체(커맨드객체)
	 * @param images : 제출된 file 타입 input 태그
	 * @return
	 */
	int boardWrite(Board inputBoard, MultipartFile boardImage);

	
	/**
	 * 게시글 조회
	 * @return
	 */
	List<Board> boardMain();

	/** 게시글 삭제
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int boardDelete(int boardNo, int memberNo);

	/**
	 *  게시글 수정
	 * @param boardNo
	 * @return
	 */
	Board updateView(int boardNo);

	/** 게시글 수정2
	 * @param boardNo 
	 * @param inputBoard
	 * @param boardImg
	 * @return
	 */
	int boardUpdate(Board inputBoard, MultipartFile boardImage);

}
