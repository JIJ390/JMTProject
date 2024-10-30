package edu.kh.jmt.restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.mapper.MainSearchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainSearchServiceImpl implements MainSearchService{
	
	private final MainSearchMapper mapper;

	// 검색 기능
	@Override
		public List<RestaurantDto>  searchResult(String searchCode, String tag, String region, int memberNo, String result) {
		List<RestaurantDto>   searchResult = new ArrayList<>();
		if(tag == null) {
			tag = "";
		}
		if(region == null) {
			region = "";
		}
		
		if(result.equals("1")) {
			searchResult = mapper.latest(searchCode, tag, region,memberNo);
		}else if (result.equals("2")) {
			searchResult =  mapper.searchResult(searchCode, tag, region,memberNo);
		}else {
			searchResult = mapper.likeOrder(searchCode, tag, region,memberNo);
		}
		
		
		
			
		
			
		log.debug("================================searchResult : {} ", searchResult);
		
			return searchResult;
		}
	
	

	
	
	
}
