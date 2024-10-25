package edu.kh.jmt.main.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.kh.jmt.main.service.MainService;
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
				Model model
				) {

			
			// 좋아요 순서
			List<RestaurantDto> listLike = service.listLike();
			//최신순
			List<RestaurantDto> listCurrent= service.listCurrent();
			//리뷰 많은 슌
			List<RestaurantDto> review= service.listReview();
			
			
			
			model.addAttribute("listLike", listLike);
			model.addAttribute("listCurrent", listCurrent);
			model.addAttribute("review", review);
			
			
			return "main/main";

		}

		
		
		
		

		

	}
