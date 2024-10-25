package edu.kh.jmt.restaurant.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.common.util.FileUtil;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;
import edu.kh.jmt.restaurant.service.RestaurantService;
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
	@GetMapping("review/reviewUpload")
	public String reviewUpload(
			@RequestParam("getRestaurantNo") int restaurantNo, 
			@RequestParam("content") String content,
			@RequestParam("likeFl") String likeFl, 
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
		result = service.reviewUpload(restaurantNo, content, likeFl, 1);

		if (result > 0) {
			ra.addFlashAttribute("message", "리뷰 등록이 완료되었습니다");
		}

		return "redirect:/restaurant/view";
	}

	@GetMapping("review/selectReview")
	public String selectReview(@RequestParam("rowNum") int rowNum, @RequestParam("restaurantNo") int restaurantNo,
			@RequestParam("sort") int sort, Model model) {

		List<ReviewDto> reviews = service.selectReview(restaurantNo, rowNum, sort);

		if(reviews.size() > 0) {
			model.addAttribute("reviews", reviews);

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
			Model model
			) {
		
		int result = service.reviewDelete(reviewNo);
		if(result > 0) {
			model.addAttribute("message", "삭제되었습니다");
		}
		
		// 레스토랑 넘버로 리다이렉트 추가
		return "redirect:/restaurant/view";
	}
			
	
	// 리뷰 업데이트 페이지 넘어가기
	@PostMapping("review/update")
	public String Update(
			@RequestParam("reviewNo" ) int reviewNo,
			@RequestParam("restaurantNo") int restaurantNo,
			Model model
			) {

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

		return "redirect:/restaurant/view";
	}
	
}
