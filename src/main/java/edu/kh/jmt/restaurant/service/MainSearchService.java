package edu.kh.jmt.restaurant.service;

import java.util.List;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

public interface MainSearchService {

	/** 검색 기능
	 * 
	 * @param searchCode
	 * @param region 지역 카테고리
	 * @param tag 음식 카테고리
	 * @return
	 */
	List<RestaurantDto> searchResult(String searchCode, String tag, String region);


	
	
	
	
	
}
