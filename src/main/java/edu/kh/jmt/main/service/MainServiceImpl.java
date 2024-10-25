package edu.kh.jmt.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import edu.kh.jmt.main.mapper.MainMapper;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;
	

//인기순 SQL
	@Override
	public List<RestaurantDto> listLike(){
		return mapper.listLike();
	}
	
	// 최신순
	@Override
	public List<RestaurantDto> listCurrent() {
		return mapper.listCurrent();
	}
	@Override
	public List<RestaurantDto> listReview() {
		return mapper.listReview();
	}
	
	
	
	// 찜하기 
	@Override
	public int storeLike(int memberNo, int restaurantNo) {
		
		// 좋아요 누른적있나 검사하기
		int result = 0;
		//기본값 0
		result = mapper.storeLike(memberNo,restaurantNo);
		//누른적이있다면 1
		//누른적이없다면 0
		
		int result2 = 0;
		
		if(result == 1) {
			result2 = mapper.deleteLike(memberNo,restaurantNo);
		}else {
			result2 = mapper.insertLike(memberNo,restaurantNo);
		}

		System.out.printf("%d", result);
		
		
		return result;
	}
}
