package edu.kh.jmt.restaurant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;

@Mapper
public interface RestaurantMapper {

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
	List<ReviewDto> selectReview(
			@Param("restaurantNo") int restaurantNo, 
			@Param("reviewRownum")int reviewRownum);

	// 리뷰 수정하기 위한 조회
	ReviewDto selectUserReview(int reviewNo);

}
