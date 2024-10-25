package edu.kh.jmt.main.service;

import java.util.List;
import java.util.Map;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

public interface MainService {

	//메인페이지 값 출력
	List<RestaurantDto> listLike();

	//최신순
	List<RestaurantDto> listCurrent();
	//리뷰 많은 순
	List<RestaurantDto> listReview();

	
	/** 메인 찜하기
	 * 
	 * @param memberNo
	 * @param restaurantNo
	 * @return
	 */
	int storeLike(int memberNo, int restaurantNo);



}
