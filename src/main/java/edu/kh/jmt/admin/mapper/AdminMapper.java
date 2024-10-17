package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.admin.dto.Restaurant;
import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface AdminMapper {

	/**
	 * 카테고리 리스트 불러오기
	 * @return
	 */
	List<Map<String, String>> selectCategoryList();

	/**
	 * 지역 리스트 불러오기
	 * @return
	 */
	List<Map<String, String>> selectLocationList();
	
	
	/**
	 * 가게 정보 등록
	 * @param insertRestaurant
	 * @param menuList
	 * @return
	 */
	int restaurantInsert(
			@Param("insertRestaurant") Restaurant insertRestaurant, 
			@Param("menuList") List<Map<String, String>> menuList);


}
