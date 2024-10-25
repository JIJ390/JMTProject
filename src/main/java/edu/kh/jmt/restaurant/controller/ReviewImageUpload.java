package edu.kh.jmt.restaurant.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import edu.kh.jmt.restaurant.dto.ReviewDto;
import edu.kh.jmt.restaurant.service.RestaurantService;
import edu.kh.jmt.restaurant.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewImageUpload {

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
			RedirectAttributes ra
	) {

		if (likeFl.equals("추천")) {
			likeFl = "Y";
		} else {
			likeFl = "N";
		}
		
		// 로그인한 회원 번호로 등록예정 1 -> loginMemberNo
		int result = service.reviewUpload(restaurantNo, content, likeFl, 1);
		
		log.debug("result : {}", result);
		
		if(result > 0) {
			ra.addFlashAttribute("message","리뷰 등록이 완료되었습니다");
		}

		return "redirect:/restaurant/view";
	}

	@GetMapping("review/selectReview")
	public String selectReview(
			@RequestParam("rowNum") int rowNum,
			@RequestParam("restaurantNo") int restaurantNo,
			@RequestParam("sort") int sort,
			Model model
			) {
		
		List<ReviewDto> reviews = service.selectReview(restaurantNo, rowNum, sort);
		
		model.addAttribute("reviews", reviews);
		
		System.out.println(reviews);
		
		return "restaurant/review";
		
	}
	
	
	
	
	
	
}
