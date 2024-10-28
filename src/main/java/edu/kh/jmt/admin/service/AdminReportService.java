package edu.kh.jmt.admin.service;

import java.util.Map;

import edu.kh.jmt.admin.dto.ReportBoard;
import edu.kh.jmt.admin.dto.ReportComment;
import edu.kh.jmt.admin.dto.ReportReview;

public interface AdminReportService {

	
	/**
	 * 검색 아닐 시 리뷰 신고 목록
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectReportReviewList(int cp);

	/**
	 * 검색일 때 리뷰 신고 목록
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> searchReportReviewList(int cp, Map<String, Object> paramMap);

	
	
	/**
	 * 리뷰 신고 상세 조회
	 * @param reportReviewNo
	 * @return
	 */
	Map<String, Object> reportReviewDetail(int reportReviewNo);

	/**
	 * 리뷰 신고 처리 
	 * @param reportReview
	 * @return
	 */
	int reportReviewFeed(ReportReview reportReview);

	
	/**
	 * 리뷰 삭제 처리
	 * @param reviewNo
	 * @return
	 */
	int reportReviewDelete(int reviewNo);

	
	
	///////////////////////////////////////////////
	// 게시글 신고

	/**
	 * 검색 아닐 시 게시글 신고 목록
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectReportBoardList(int cp);

	/**
	 * 검색일 때 게시글 신고 목록
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> searchReportBoardList(int cp, Map<String, Object> paramMap);

	
	
	/**
	 * 게시글 신고 상세 조회
	 * @param reportBoardNo
	 * @return
	 */
	Map<String, Object> reportBoardDetail(int reportBoardNo);

	/**
	 * 게시글 신고 처리 
	 * @param reportBoard
	 * @return
	 */
	int reportBoardFeed(ReportBoard reportBoard);

	
	/**
	 * 게시글 삭제 처리
	 * @param boardNo
	 * @return
	 */
	int reportBoardDelete(int boardNo);
	
	
	
	////////////////////////////////////////////////////
	////////////////////////////////////////////////////
	////////////////////////////////////////////////////
	// 댓글 신고
	////////////////////////////////////////////////////
	
	
	

	/**
	 * 검색 아닐 시 게시글 신고 목록
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectReportCommentList(int cp);

	/**
	 * 검색일 때 게시글 신고 목록
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> searchReportCommentList(int cp, Map<String, Object> paramMap);

	
	
	/**
	 * 게시글 신고 상세 조회
	 * @param reportCommentNo
	 * @return
	 */
	Map<String, Object> reportCommentDetail(int reportCommentNo);

	/**
	 * 게시글 신고 처리 
	 * @param reportComment
	 * @return
	 */
	int reportCommentFeed(ReportComment reportComment);

	
	/**
	 * 게시글 삭제 처리
	 * @param commentNo
	 * @return
	 */
	int reportCommentDelete(int commentNo);
	
}
