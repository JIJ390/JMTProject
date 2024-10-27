package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

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
	
	
	/**
	 * 상세 조회 대상 리뷰 신고 정보와 작성자 정보 조회
	 * @param reportReviewNo
	 * @return
	 */
	@GetMapping("review/{reportReviewNo:[0-9]+}")
	@ResponseBody
	public Map<String, Object> reportReviewDetail (
			@PathVariable("reportReviewNo") int reportReviewNo)	 {
		
		Map<String, Object> map = service.reportReviewDetail(reportReviewNo);
		
		log.debug("aaaa : {}", map);
		log.debug("aaaa : {}", map);
		log.debug("aaaa : {}", map);
		log.debug("aaaa : {}", map);
		
		return service.reportReviewDetail(reportReviewNo);
		
	}
	
	@PostMapping("review/feed")
	public String reportReviewFeed (
			@ModelAttribute ReportReview reportReview,
			RedirectAttributes ra) {
		
		log.debug("aaaa : {}", reportReview);
		log.debug("aaaa : {}", reportReview);
		log.debug("aaaa : {}", reportReview);
		log.debug("aaaa : {}", reportReview);
		
		int result = service.reportReviewFeed(reportReview);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "처리되었습니다");
		}	else {
			ra.addFlashAttribute("message", "처리 실패");
		}
		
		return "redirect:/admin/report/review";
		
	}
	
}
