package edu.kh.jmt.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.board.dto.Board;
import edu.kh.jmt.restaurant.dto.ReviewDto;

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
	Map<String, Object> boardMain(int cp);
	
	
	/**
	 * 게시글 목록 검색시
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> searchBoardMain(int cp, Map<String, Object> paramMap);

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


	/**
	 * 게시글 신고
	 * @param reportType
	 * @param reportContent
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int boardReport(String reportType, String reportContent, int boardNo, int memberNo);


	/**
	 * 댓글신고
	 * @param reportType
	 * @param content
	 * @param commentNo
	 * @param memberNo
	 * @return
	 */
	int commentReport(String reportType, String content, int commentNo, int memberNo);


	







}
