package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.admin.dto.ReportBoard;
import edu.kh.jmt.admin.dto.ReportComment;
import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.myPage.dto.Member;

@Mapper
public interface AdminReportMapper {

	/**
	 * 검색 아닐 때 신고 목록 수
	 * @return
	 */
	int getReportReviewCount();

	/**
	 * 검색 아닐 때 신고 목록
	 * @param rowBounds
	 * @return
	 */
	List<ReportReview> selectReportReviewList(RowBounds rowBounds);

	/**
	 * 검색일 때 신고 목록 수
	 * @param paramMap
	 * @return
	 */
	int searchReportReviewCount(Map<String, Object> paramMap);

	/**
	 * 검색일 때 신고 목록
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<ReportReview> searchReportReviewList(Map<String, Object> paramMap, RowBounds rowBounds);

	
	/**
	 * 리뷰 신고 상세 조회
	 * @param reportReviewNo
	 * @return
	 */
	ReportReview reportReviewDetail(int reportReviewNo);

	
	/**
	 * 신고 대상 리뷰 작성자 정보 조회
	 * @param reviewMemberNo
	 * @return
	 */
	Member selectReviewMember(int reviewMemberNo);

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

	

	
	
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	// 게시글 신고
	
	
	
	/**
	 * 검색 아닐 때 신고 목록 수
	 * @return
	 */
	int getReportBoardCount();

	/**
	 * 검색 아닐 때 신고 목록
	 * @param rowBounds
	 * @return
	 */
	List<ReportBoard> selectReportBoardList(RowBounds rowBounds);

	/**
	 * 검색일 때 신고 목록 수
	 * @param paramMap
	 * @return
	 */
	int searchReportBoardCount(Map<String, Object> paramMap);

	/**
	 * 검색일 때 신고 목록
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<ReportBoard> searchReportBoardList(Map<String, Object> paramMap, RowBounds rowBounds);

	
	/**
	 * 게시글 신고 상세 조회
	 * @param reportBoardNo
	 * @return
	 */
	ReportBoard reportBoardDetail(int reportBoardNo);

	
	/**
	 * 신고 대상 게시글 작성자 정보 조회
	 * @param boardMemberNo
	 * @return
	 */
	Member selectBoardMember(int boardMemberNo);

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

	
	
	
	
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	// 댓글 신고

	
	/**
	 * 검색 아닐 때 신고 목록 수
	 * @return
	 */
	int getReportCommentCount();

	/**
	 * 검색 아닐 때 신고 목록
	 * @param rowBounds
	 * @return
	 */
	List<ReportComment> selectReportCommentList(RowBounds rowBounds);

	/**
	 * 검색일 때 신고 목록 수
	 * @param paramMap
	 * @return
	 */
	int searchReportCommentCount(Map<String, Object> paramMap);

	/**
	 * 검색일 때 신고 목록
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<ReportComment> searchReportCommentList(Map<String, Object> paramMap, RowBounds rowBounds);

	
	/**
	 * 댓글 신고 상세 조회
	 * @param reportCommentNo
	 * @return
	 */
	ReportComment reportCommentDetail(int reportCommentNo);

	
	/**
	 * 신고 대상 댓글 작성자 정보 조회
	 * @param CommentMemberNo
	 * @return
	 */
	Member selectCommentMember(int boardMemberNo);

	/**
	 * 댓글 신고 처리
	 * @param reportComment
	 * @return
	 */
	int reportCommentFeed(ReportComment reportComment);

	/**
	 * 댓글 삭제 처리
	 * @param CommentNo
	 * @return
	 */
	int reportCommentDelete(int CommentNo);

}
