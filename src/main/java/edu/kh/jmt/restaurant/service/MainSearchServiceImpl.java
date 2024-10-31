package edu.kh.jmt.restaurant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.jmt.admin.dto.AdminPagination;
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
		public Map<String, Object> searchResult(String searchCode, String tag, String region, int memberNo, String result, int cp) {
		
		
		
		List<RestaurantDto>   searchResult = new ArrayList<RestaurantDto>();
		
		int listCount = 0;
		
		
		if(tag == null) {
			tag = "";
		}
		if(region == null) {
			region = "";
		}
		
		if(result.equals("1")) {
			listCount = mapper.latestCount(searchCode, tag, region,memberNo);
		}else if (result.equals("2")) {
			listCount = mapper.searchResultCount(searchCode, tag, region,memberNo);
		}else {
			listCount = mapper.likeOrderCount(searchCode, tag, region,memberNo);
		}
		
	
		
		
		AdminPagination pagination = new AdminPagination(cp, listCount);
			
		int limit = pagination.getLimit();
		int offset = (cp - 1)* limit;
			
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		
		
		if(result.equals("1")) {
			searchResult = mapper.latest(searchCode, tag, region,memberNo, rowBounds);
		}else if (result.equals("2")) {
			searchResult =  mapper.searchResult(searchCode, tag, region,memberNo, rowBounds);
		}else {
			searchResult = mapper.likeOrder(searchCode, tag, region,memberNo, rowBounds);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put(("searchResult"), searchResult);
		map.put(("pagination"), pagination);		
		
		return map;
		

		
		
		

	}
	
	

	
	
	
}
