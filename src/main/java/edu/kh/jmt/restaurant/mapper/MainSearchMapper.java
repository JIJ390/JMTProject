package edu.kh.jmt.restaurant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

@Mapper
public interface MainSearchMapper {

	/** 검색 기능
	 * 
	 * @param searchCode
	 * @param region 지역
	 * @param tag 음식
	 * @return
	 */
	List<RestaurantDto> searchResult(@Param ("searchCode") String searchCode, 
									 @Param ("tag") String tag, 
									 @Param ("region") String region);


	
	
	
	
	
}
