package edu.kh.jmt.admin.service;

import java.util.Map;

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


}
