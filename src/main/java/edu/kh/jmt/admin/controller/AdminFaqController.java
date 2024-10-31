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
import edu.kh.jmt.admin.service.AdminFaqService;
import edu.kh.jmt.noticeView.dto.Faq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("admin/faq")
@RequiredArgsConstructor
public class AdminFaqController {

	private final AdminFaqService service;


	 /** FAQ 리스트 가져오기
	 * 
	 * @param cp
	 * @param model
	 * @return */
		
		
		@GetMapping("") 
		public String selectNoticeList(
	  @RequestParam(value="cp", required = false, defaultValue = "1") int cp, Model
	  model) {
	  
	  Map<String, Object> map = service.selectFaqList(cp);
	  
	  List<Faq> faqList = (List<Faq>)map.get("faqList"); 
	  AdminPagination pagination
	  = (AdminPagination)map.get("pagination");
	  
	  model.addAttribute("faqList", faqList); model.addAttribute("pagination",
	  pagination);
 
	  return "admin/faqManage"; }
	  
	  
		/**
		 * FAQ 삭제
		 * @param faqNo
		 * @param ra
		 * @return
		 */
	  @PostMapping("delete") public String deleteFaq (
	  @RequestParam("faqNo") int faqNo, 
	  RedirectAttributes ra ) {
	  
	  int result = service.deleteFaq(faqNo);
	  
	  if (result > 0) { 
	  	ra.addFlashAttribute("message", "삭제 되었습니다."); 
	  	} else {
	  		ra.addFlashAttribute("message", "삭제 실패!"); 
	  		}
	  
	  return "redirect:/admin/faq";
	  
	  }
	  
	  
	/**
	 * FAQ 신규 등록 페이지 이동
	 */
	
	  @GetMapping("insert") public String insertFaqView() { 
	  	return "/admin/faqInsert"; 
	  }

	 /**
			 * FAQ 등록
			 * 
			 * @return
			 */

	 @PostMapping("insert") public String insertFaq(
	 	 @ModelAttribute Faq faq, 
	 	 RedirectAttributes ra) {
	 
	 
		 int result = service.insertFaq(faq);
		 
		 if (result > 0) { 
			 ra.addFlashAttribute("message", "FAQ가 등록 되었습니다."); 
			 } else {
				 ra.addFlashAttribute("message", "등록 실패!"); 
				 }
		 
		 return "redirect:/admin/faq"; 
		 
	 }
	 
	 
 	/**
		 * FAQ 수정 페이지 이동
		 * 
		 * @param noticeNo : 수정하는 FAQ 번호
		 * @param model
		 * @return
		 */
				 @PostMapping("updateView") public String updateNoticeView(
				 @RequestParam("faqNo") int faqNo, 
				 Model model) {
				  
				 Faq faq = service.updateFaqView(faqNo);
				  
				 model.addAttribute("faq", faq);
				  
				 return "/admin/faqUpdate";
				  
			 }
					 
		
			/**
			 * FAQ 수정
			 * @param faqNo
			 * @param ra
			 * @return
			 */
			@PostMapping("faqEdit") 
			public String updateFaq (
			@ModelAttribute Faq faq,
		  RedirectAttributes ra ) {
		  
		  int result = service.updateFaq(faq);
		  
		  if (result > 0) { 
		  	ra.addFlashAttribute("message", "수정 되었습니다."); 
		  	} else {
		  		ra.addFlashAttribute("message", "수정 실패!"); 
		  		}
		  
		  return "redirect:/admin/faq";
		  
		  }
					 
} // end
