package edu.kh.jmt.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.jmt.main.service.MainService;
import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j

public class MainController {
	
	
	//메인페이지 열면서 메인페이지 값 출력할거 가져오기
	private final MainService service;

		@RequestMapping("/")
		public String main(
				Model model,
				@SessionAttribute(value = "loginMember", required=false) 
				Member loginMember
				
				) {
			int memberNo =  loginMember == null ? 0 : loginMember.getMemberNo();
			
			// 좋아요 순서
			List<RestaurantDto> listLike = service.listLike(memberNo);
			//최신순
			List<RestaurantDto> listCurrent= service.listCurrent(memberNo);
			//리뷰 많은 슌
			List<RestaurantDto> review= service.listReview(memberNo);
			
			
			
			model.addAttribute("listLike", listLike);
			model.addAttribute("listCurrent", listCurrent);
			model.addAttribute("review", review);
			
			
			return "main/main";

		}

		
		
		//메인 찜하기 표시하기
		@ResponseBody
		@PostMapping("/like")
		public int storeLike(
				@RequestBody int restaurantNo1,
				@SessionAttribute("loginMember") Member loginMember
				) {
			int memberNo = loginMember.getMemberNo();
			

			return service.storeLike(memberNo,restaurantNo1);
		}
		
		
		

		

	}
