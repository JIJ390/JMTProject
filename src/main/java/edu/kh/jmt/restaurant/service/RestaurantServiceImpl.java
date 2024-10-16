package edu.kh.jmt.restaurant.service;

import org.springframework.stereotype.Service;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{
	
	private final RestaurantMapper mapper;

	// 식당 번호로 상세정보 조회
	@Override
	public RestaurantDto restaurantDetail(int restaurantNo) {
		return mapper.restaurantDetail(restaurantNo);
	}
	
}
