package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.dto.Restaurant;
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
		
		// 페이지 네이션, 가게 정보 묶어서 담을 객체
		Map<String, Object> map = null;
		
		
		if (paramMap.get("key") == null) {	
			map = service.selectReportReviewList(cp);
			
		} else { // 검색한 경우
			
			// paramMap 에 key, query 담겨 있음
			map = service.searchReportReviewList(cp, paramMap);
		}
		
		List<ReportReview> reportReviewList = (List<ReportReview>)map.get("reportReviewList");
		AdminPagination adminPagination = (AdminPagination)map.get("pagination");
		
		
		model.addAttribute("reportReviewList", reportReviewList);
		model.addAttribute("pagination", adminPagination);
		
		return "admin/reportReview";
	}
}
