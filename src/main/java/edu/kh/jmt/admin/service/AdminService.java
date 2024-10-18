package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Restaurant;

public interface AdminService {

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


}
