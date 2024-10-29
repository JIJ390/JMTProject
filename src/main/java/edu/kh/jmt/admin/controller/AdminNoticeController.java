package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.service.AdminNoticeService;
import edu.kh.jmt.noticeView.dto.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("admin/notice")
@RequiredArgsConstructor
public class AdminNoticeController {
	
	private final AdminNoticeService service;

	
	/**
	 * 공지사항 리스트 가져오기
	 * @param cp
	 * @param model
	 * @return
	 */
	@GetMapping("")
	public String selectNoticeList(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectNoticeList(cp);
		
		List<Notice> noticeList = (List<Notice>)map.get("noticeList");
		AdminPagination pagination = (AdminPagination)map.get("pagination");
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("pagination", pagination);
		
		return "admin/noticeManage";
	}
	
	
	@PostMapping("delete")
	public String deleteNotice (
			@RequestParam("noticeNo") int noticeNo,
			RedirectAttributes ra 
			) {
				
		int result = service.deleteNotice(noticeNo);
				
		if (result > 0) {
			ra.addFlashAttribute("message", "삭제 되었습니다");
		} else {
			ra.addFlashAttribute("message", "삭제 실패");
		}
				
		return "redirect:/admin/notice";
		
	}
	
	
	/**
	 * 공지사항 등록 페이지 이동
	 */
	@GetMapping("insert")
	public String insertNoticeView() {
		return "/admin/noticeInsert";
	}
	
	
	/**
	 * 공지 사항 등록
	 * @return
	 */
	@PostMapping("insert")
	public String insertNotice(
			@ModelAttribute Notice notice,
			RedirectAttributes ra) {
		
		
		int result = service.insertNotice(notice);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "공지사항이 등록 되었습니다");
		} else {
			ra.addFlashAttribute("message", "등록 실패");
		}
		
		return "redirect:/admin/notice";
	}
	
	
	/**
	 * 공지 사항 수정 페이지 이동
	 * @param noticeNo : 수정하련느 공지사항 번호
	 * @param model
	 * @return
	 */
	@PostMapping("updateView")
	public String updateNoticeView(
			@RequestParam("noticeNo") int noticeNo,
			Model model) {
		
		Notice notice = service.updateNoticeView(noticeNo);
		
		model.addAttribute("notice", notice);
		
		return "/admin/noticeUpdate";
		
	}
	
	@PostMapping("update")
	public String updateNotice(
			@RequestParam("noticeNo") int noticeNo,
			RedirectAttributes ra) {
		
		int result = service.updateNotice(noticeNo);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "공지사항이 수정 되었습니다");
		} else {
			ra.addFlashAttribute("message", "수정 실패");
		}
		
		return "redirect:/admin/notice";
	}
}
