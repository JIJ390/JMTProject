package edu.kh.jmt.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.mapper.AdminReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminReportServiceImpl implements AdminReportService{

	private final AdminReportMapper mapper;
	
	// 리뷰 신고 목록 가져오기
	@Override
	public List<ReportReview> reportReviewList() {
		
		List<ReportReview> reportReviewList = mapper.reportReviewList();
		
		return mapper.reportReviewList();
	}
	
	
}
