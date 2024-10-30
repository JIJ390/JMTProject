package edu.kh.jmt.restaurant.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.service.MainSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainSearchController {

	private final MainSearchService service;
	
	
	
	//---------- search 화면전환
	//  화면전환 값 넘기기
	@GetMapping("search")
	public String searchPage(
			@RequestParam("searchCode") String searchCode,
			RedirectAttributes ra,
			Model model,
			RestaurantDto restaurantDto,
			@RequestParam(value = "tag", 	required = false) String tag,
			@RequestParam(value = "region", required = false)  String region,
			@SessionAttribute(value="loginMember", required= false) Member loginMember,
			@RequestParam(value="result", required= false) String result
			
			) throws UnsupportedEncodingException {
		
		int memberNo =  loginMember == null ? 0 : loginMember.getMemberNo();
		
//		log.debug("=======View searchBar 값 : {}", searchCode );
		// 값 가져올수 있음
		
		log.debug("====================== result 전저넞너저너전저너전 : {}", result);
		
		if(result.equals("0")) {
			result = "1";
			
		}
		else if(result.equals("최신순")) {
			result = "1";
			
		}
		else if(result.equals("리뷰 많은 순")) {
			result = "2";
			
		}else {
			result = "3";
		}
		log.debug("====================== result  후후후후ㅜ후후ㅜ후후후후후후후후후훟 : {}", result);
		
		
		List<RestaurantDto> searchResult = service.searchResult(searchCode, tag, region,memberNo, result);
		
		model.addAttribute("searchResult", searchResult);
		
		if(tag == null) {
			model.addAttribute("tag", "");
		}else {
			model.addAttribute("tag", tag);
		}
		
		if(region == null) {
			model.addAttribute("region", "");
		}else {
			model.addAttribute("region", region);
		}
		
		model.addAttribute("searchCode", searchCode);
		
		
		return "main/search";
	}
	
	
	
	
	
	
}
