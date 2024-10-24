package edu.kh.jmt.restaurant.service;

import java.util.List;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

public interface MainSearchService {

	/** 검색 기능
	 * 
	 * @param searchCode
	 * @return
	 */
	List<RestaurantDto> searchResult(String searchCode);


	
	
	
	
	
}
