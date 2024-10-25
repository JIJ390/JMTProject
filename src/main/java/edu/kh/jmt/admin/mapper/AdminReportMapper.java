package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.dto.Restaurant;

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

	

	
}
