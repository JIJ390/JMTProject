package edu.kh.jmt.noticeView.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.noticeView.dto.Faq;
import edu.kh.jmt.noticeView.dto.Notice;
import edu.kh.jmt.noticeView.dto.NoticePagination;
import edu.kh.jmt.noticeView.service.NoticeViewService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("notice")
public class NoticeViewController {
	
	private final NoticeViewService service;
	
	/**
	 * 공지사항 페이지로 전환
	 */
//	@GetMapping("notice")
//	public String notice() {
//		return "/notice/notice";
//	}
	
	
	/**
	 * 
	 * @param cp
	 * @param model
	 * @return
	 */
	@GetMapping("noticeView")
	public String selectNoticeList(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectNoticeList(cp);
		
		List<Notice> noticeList = (List<Notice>)map.get("noticeList");
		NoticePagination pagination = (NoticePagination)map.get("pagination");
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagination", pagination);
		
		if(cp == 1) model.addAttribute("newMark", true);
		
		return "notice/noticeView";
	}
	
	
	/* 공지사항 자세히 보기 */
	@GetMapping("noticeView/{noticeNo:[0-9]+}")
	public String detailViewNotice(
			@PathVariable("noticeNo") int noticeNo,
			Model model,
			RedirectAttributes ra
			) {
		
		Notice detailviewNotice = service.detailViewNotice(noticeNo);
		
		
		
		model.addAttribute("notice", detailviewNotice);
		
		return "notice/noticeDetail";
	}
	
	
	/**
	 * FAQ 리스트 불러오기
	 * @param model
	 * @return
	 */
	@GetMapping("faq")
	public String selectFaqList(
			Model model) {
			List<Faq> faqList = service.selectFaqList();
			
			model.addAttribute("faqList", faqList);
		
		return "notice/faq";
	}
	
	

} // end
