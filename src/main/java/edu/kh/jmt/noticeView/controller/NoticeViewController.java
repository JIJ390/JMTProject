package edu.kh.jmt.noticeView.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.jmt.noticeView.dto.Notice;
import edu.kh.jmt.noticeView.dto.Pagination;
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
	@GetMapping("notice")
	public String selectNoticeList(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectNoticeList(cp);
		
		List<Notice> noticeList = (List<Notice>)map.get("noticeList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagination", pagination);
		
		if(cp == 1) model.addAttribute("newMark", true);
		
		return "notice/notice";
	}

}
