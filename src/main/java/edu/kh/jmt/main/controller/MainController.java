package edu.kh.jmt.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.main.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	
	
	private final MainService service;

		@RequestMapping("/")
		public String main() {

			return "main/main";

		}

		//  화면전환
		@GetMapping("/search")
		public String searchPage(
				@RequestParam("searchBar") String searchBar,
				RedirectAttributes ra
				) {
			String result = null;
			
//			log.debug("=======View searchBar 값 : {}", searchBar );
			// 값 가져올수 있음
			
			
			//임시 문자열을 얻어와 검색창에 맞게 대입해주는. 
			//sql 수정 필요. 
			result = service.searchPage(searchBar);

			
			log.debug("=======View searchBar 값 : {}", result );
			

			return "main/search";
		}

	}
