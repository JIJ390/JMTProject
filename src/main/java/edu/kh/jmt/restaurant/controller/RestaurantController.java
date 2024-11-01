package edu.kh.jmt.restaurant.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PropertySource("classpath:/config.properties")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("restaurant")
public class RestaurantController {

	private final RestaurantService service;

	@Value("${my.map.api-key}")
	private String apiKey;

	@GetMapping("view")
	public String view(Model model, @RequestParam("restaurantNo") int restaurantNo,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {

//		 정보 전달용 레스토랑 객체
//		 ** 구현 예정 -> restaurantNo를 파라미터로 얻어와 조회할 것
//									예시로 3 넣어둔것
		int likeCheck = 0;
		RestaurantDto restaurant = service.restaurantDetail(restaurantNo);
//		List<ReviewDto> reviews = service.selectReview(13, 1); 
		if (loginMember != null) {
			likeCheck = service.likeCheck(restaurantNo, loginMember.getMemberNo());
		}
		
		model.addAttribute("apiKey", apiKey);
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("likeCheck", likeCheck);
//		model.addAttribute("review", reviews.get(0));

		return "restaurant/restaurantDetail";
	}

	@PostMapping("add")
	public String add(@RequestParam("restaurantNo") int restaurantNo, @RequestParam("memberNo") int memberNo,
			Model model) {

		model.addAttribute("restaurant", service.restaurantDetail(restaurantNo));

		return "restaurant/restaurantDetailAdd";
	}

	@GetMapping("add")
	public String add() {
		return "redirect:/myPage/loginPage";
	}

	@GetMapping("returnView")
	public String returnView() {
		return "redirect:view";
	}
	
	@ResponseBody
	@GetMapping("bookMark")
	public int boorMark(
			@RequestParam("restaurantNo") int restaurantNo,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		System.out.println(restaurantNo);
		System.out.println(loginMember);
		
		System.out.println(restaurantNo);
		System.out.println(loginMember);
		
		System.out.println(restaurantNo);
		System.out.println(loginMember);
		
		int result = service.likeCheck(restaurantNo, loginMember.getMemberNo());
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		System.out.println(result);
		
		return result;
	}
	
	
	
	@ResponseBody
	@GetMapping("bookMarkAdd")
	public int boorMarkAdd(
			@RequestParam("restaurantNo") int restaurantNo,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int result = service.bookmarkadd(restaurantNo, loginMember.getMemberNo());
		
		return result;
	}
	
	@ResponseBody
	@GetMapping("bookMarkDelete")
	public int boorMarkdelete(
			@RequestParam("restaurantNo") int restaurantNo,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		int result = service.bookmarkdelete(restaurantNo, loginMember.getMemberNo());
		
		return result;
	}
	

}
