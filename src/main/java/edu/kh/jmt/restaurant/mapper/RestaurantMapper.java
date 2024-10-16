package edu.kh.jmt.restaurant.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

@Mapper
public interface RestaurantMapper {

	/**
	 * 식당 번호로 상세정보 조회
	 * @param restaurantNo : 전달받은 식당 번호 
	 * @return restaurant : restaurantDto (가게 정보가 담긴 dto)
	 */
	RestaurantDto restaurantDetail(int restaurantNo);

}
