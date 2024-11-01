package edu.kh.jmt.restaurant.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;
import edu.kh.jmt.restaurant.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

	private final ReviewService service;

	@ResponseBody
	@PostMapping("summerNoteUpload")
	public String summerNoteUpload(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return "파일이 없습니다.";
		}

		String renamePath = service.imgRename(file);

		// 이미지명 변경 후 저장된 url 리턴
		return renamePath;
	}

	/**
	 * 리뷰 업로드
	 * 
	 * @param restaurantNo
	 * @param content
	 * @param likeFl
	 * @return
	 */
	@PostMapping("review/reviewUpload")
	public String reviewUpload(
			@RequestParam("getRestaurantNo") int restaurantNo, 
			@RequestParam("content") String content,
			@RequestParam("likeFl") String likeFl, 
			RedirectAttributes ra,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember
			) {

		if(loginMember == null) {
			return "redirect:/myPage/loginPage";
		}
		 
		int result = 0;


		// 리뷰 수정
		if (likeFl.equals("추천")) {
			likeFl = "Y";
		} else {
			likeFl = "N";
		}

		// 로그인한 회원 번호로 등록예정 1 -> loginMemberNo
		result = service.reviewUpload(restaurantNo, content, likeFl, loginMember.getMemberNo());

		if (result > 0) {
			ra.addFlashAttribute("message", "리뷰 등록이 완료되었습니다");
		}

		return "redirect:/restaurant/view?restaurantNo=" + restaurantNo;
	}

	@GetMapping("review/selectReview")
	public String selectReview(
			@RequestParam("rowNum") int rowNum, 
			@RequestParam("restaurantNo") int restaurantNo,
			@RequestParam("sort") int sort, 
			Model model
			) {

	
		
		List<ReviewDto> reviews = service.selectReview(restaurantNo, rowNum, sort);

		int size = service.reviewSize(restaurantNo);

		if(rowNum >= size) {
			model.addAttribute("maxSize", 1);
		}
		
		if(reviews.size() > 0) {
			model.addAttribute("reviews", reviews);
			model.addAttribute("reviewSize", reviews.size());
			
		return "restaurant/review";
		}
		else {
			return "restaurant/noReview";
		}
			
		
		

	}

	@GetMapping("review/delete")
	public String reviewDelete(
			@RequestParam("reviewNo") int reviewNo,
			@RequestParam("restaurantNo") int restaurantNo,
			Model model,
			RedirectAttributes ra
			) {
		
		int result = service.reviewDelete(reviewNo);
		if(result > 0) {
			ra.addFlashAttribute("message", "리뷰가 삭제되었습니다");
		}
		
		// 레스토랑 넘버로 리다이렉트 추가
		return "redirect:/restaurant/view?restaurantNo="+restaurantNo;
	}
			
	
	// 리뷰 업데이트 페이지 넘어가기
	@PostMapping("review/update")
	public String Update(
			@RequestParam("reviewNo" ) int reviewNo,
			@RequestParam("restaurantNo") int restaurantNo,
			Model model,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember
			) {

		if(loginMember == null) {
			return "redirect:/myPage/loginPage";
		}
		
		 
		ReviewDto review = service.selectReview(reviewNo);
		RestaurantDto restaurant = service.selectRestaurant(restaurantNo);
		
		model.addAttribute("review", review);
		model.addAttribute("restaurant", restaurant);
		
		return "restaurant/reviewUpdate";
	}
	
	@PostMapping("review/reviewUpdate")
	public String reviewUpdate(
			@RequestParam("getReviewNo") int reviewNo,
			@RequestParam("content") String content,
			@RequestParam("likeFl") String likeFl, 
			@RequestParam("getRestaurantNo") int restaurantNo,
			RedirectAttributes ra
			) {

		int result = 0;

		// 리뷰 수정
		if (likeFl.equals("추천")) {
			likeFl = "Y";
		} else {
			likeFl = "N";
		}

		// 로그인한 회원 번호로 등록예정 1 -> loginMemberNo
		result = service.reviewUpdate(reviewNo, content, likeFl );

		if (result > 0) {
			ra.addFlashAttribute("message", "리뷰 수정이 완료되었습니다");
		}

		return "redirect:/restaurant/view?restaurantNo=" + restaurantNo;
	}
	
	@ResponseBody
	@GetMapping("review/report")
	public int reviewReport(
			@RequestParam("reportContent") String reportContent,
			@RequestParam("reportType") String reportType,
			@RequestParam("reviewNo") int reviewNo,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int result = service.reportAdd(reportType, reportContent, reviewNo, loginMember.getMemberNo());
		
		
		if(reportType.equals("")) {
			return 0;
		}
		
		return result;
	}
	
	
	
}
