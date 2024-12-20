package edu.kh.jmt.main.service;

import java.util.List;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

public interface MainService {

	//메인페이지 값 출력
	List<RestaurantDto> listLike(int memberNo);

	//최신순
	List<RestaurantDto> listCurrent(int memberNo);
	//리뷰 많은 순
	List<RestaurantDto> listReview(int memberNo);

	
	/** 메인 찜하기
	 * 
	 * @param memberNo
	 * @param restaurantNo
	 * @return
	 */
	int storeLike(int memberNo, int restaurantNo);



}
