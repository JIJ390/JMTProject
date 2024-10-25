package edu.kh.jmt.admin.service;

import java.util.List;

import edu.kh.jmt.admin.dto.ReportReview;

public interface AdminReportService {

	/**
	 * 리뷰 신고 목록 가져오기
	 * @return reportReviewList
	 */
	List<ReportReview> reportReviewList();


}
