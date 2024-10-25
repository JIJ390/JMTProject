package edu.kh.jmt.restaurant.service;

import java.util.List;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;

public interface RestaurantService {

	/**
	 * 식당 번호로 상세정보 조회
	 * @param restaurantNo : 전달받은 식당 번호 
	 * @return restaurant : restaurantDto (가게 정보가 담긴 dto)
	 */
	RestaurantDto restaurantDetail(int restaurantNo);

	/**
	 * 식당 리뷰 조회
	 * @param restaurantNo
	 * @param reviewRownum
	 * @return
	 */
	List<ReviewDto> selectReview(int restaurantNo, int reviewRownum);

	// 리뷰 수정하기 위해 리뷰 조회
	ReviewDto selectUserReview(int reviewNo);

}
