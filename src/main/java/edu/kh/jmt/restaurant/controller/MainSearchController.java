package edu.kh.jmt.restaurant.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			@RequestParam(value = "region", required = false)  String region
			
			) throws UnsupportedEncodingException {
		
		
		log.debug("================= searchCode : {} ", searchCode);
		log.debug("================= region : {} ", region);
		log.debug("================= tag : {} ", tag);
		
//		log.debug("=======View searchBar 값 : {}", searchCode );
		// 값 가져올수 있음
		
		List<RestaurantDto> searchResult = service.searchResult(searchCode, tag, region);
		
		model.addAttribute("searchResult", searchResult);
		
		return "main/search";
	}
	
	
	
	
	
	
}
