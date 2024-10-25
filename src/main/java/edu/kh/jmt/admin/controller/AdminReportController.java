package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("admin/report")
@RequiredArgsConstructor
public class AdminReportController {

	private final AdminReportService service;
	
	
	/**
	 * 리뷰 신고 게시판 이동 + 신고 목록 가져오기
	 * @return
	 */
	@GetMapping("review")
	public String reportReviewList (
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap)	{
		
		List<ReportReview> reportReviewList = service.reportReviewList();
		
		model.addAttribute("reportReviewList", reportReviewList);
		
		return "admin/reportReview";
	}
}
