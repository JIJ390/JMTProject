package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

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

	

	
}
