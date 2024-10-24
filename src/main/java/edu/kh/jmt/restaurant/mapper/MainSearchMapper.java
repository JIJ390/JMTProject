package edu.kh.jmt.restaurant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

@Mapper
public interface MainSearchMapper {

	/** 검색 기능
	 * 
	 * @param searchCode
	 * @return
	 */
	List<RestaurantDto> searchResult(String searchCode);


	
	
	
	
}
