package edu.kh.jmt.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("restaurant")
public class RestaurantController {
	
	private final RestaurantService service;
			
	@GetMapping("view")
	public String view(
//			@PathVariable("restaurantNo") int restaurantNo // 받아올 매개변수
			Model model
			) {
		
//		 정보 전달용 레스토랑 객체
//		 ** 구현 예정 -> restaurantNo를 파라미터로 얻어와 조회할 것
//									예시로 3 넣어둔것
		RestaurantDto restaurant = service.restaurantDetail(3);
		
		
		 
		model.addAttribute("restaurant", restaurant);
		
		return "restaurant/restaurantDetail";
	}
	
	
	
	
			
	@GetMapping("add")
	public String add() {
		return "restaurant/restaurantDetailAdd";
	}
	
	
	
	
	
	@GetMapping("returnView")		
	public String returnView() {
		return "redirect:view";
	}
	
	
}
