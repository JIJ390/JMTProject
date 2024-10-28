package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Restaurant;

public interface AdminRestaurantService {

	/**
	 * 카테고리 리스트 인터셉터로 찾아오기
	 * @return
	 */
	List<Map<String, String>> selectCategoryList();

	/**
	 * 지역 리스트 찾기
	 * @return
	 */
	List<Map<String, String>> selectLocationList();

	/**
	 * 가게 정보 등록
	 * @param insertRestaurant
	 * @param restaurantImges
	 * @return
	 */
	int restaurantInsert(
			Restaurant insertRestaurant, 
			List<MultipartFile> restaurantImages,
			List<String> menuNameList,
			List<String> menuPriceList);
	
	/**
	 * 검색 아닐 시 가게 리스트 가져오기
	 * @param cp
	 * @return restaurantList
	 */
	Map<String, Object> selectRestaurantList(int cp);

	/**
	 * 검색일 때 가게 정보 가져오기
	 * @param cp
	 * @param paramMap
	 * @return restaurantList
	 */
	Map<String, Object> restaurantSearchList(int cp, Map<String, Object> paramMap);


	/**
	 * 가게 정보 상세 조회
	 * @param restaurantNo
	 * @return
	 */
	Map<String, Object> restaurantDetail(int restaurantNo);

	
	/**
	 * 가게 삭제
	 * @param restaurantNo
	 * @return
	 */
	int restaurantDelete(int restaurantNo);

	/**
	 * 가게 수정용 조회
	 * @param restaurantNo
	 * @return
	 */
	Map<String, Object> restaurantUpdateView(int restaurantNo);

	
	/**
	 * 가게 정보 수정
	 * @param restaurant
	 * @param restaurantImages
	 * @param menuNameList
	 * @param menuPriceList
	 * @return
	 */
	int restaurantUpdate(Restaurant restaurant, List<MultipartFile> restaurantImages, List<String> menuNameList,
	    List<String> menuPriceList);
	
	
	
	


	


}
