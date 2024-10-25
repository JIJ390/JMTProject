package edu.kh.jmt.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.admin.dto.ReportReview;

@Mapper
public interface AdminReportMapper {

	
	/**
	 * 리뷰 신고 목록 가져오기
	 * @return reportReviewList
	 */
	List<ReportReview> reportReviewList();


	
}
