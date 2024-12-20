package edu.kh.jmt.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;
import edu.kh.jmt.restaurant.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService{
	
	private final RestaurantMapper mapper;

	// 식당 번호로 상세정보 조회
	@Override
	public RestaurantDto restaurantDetail(int restaurantNo) {
		return mapper.restaurantDetail(restaurantNo);
	}

	// 식당 리뷰 조회
	@Override
	public List<ReviewDto> selectReview(int restaurantNo, int reviewRownum) {
		return mapper.selectReview(restaurantNo, reviewRownum);
	}
	
	// 리뷰 수정하기 위한 조회
	@Override
	public ReviewDto selectUserReview(int reviewNo) {
		return mapper.selectUserReview(reviewNo);
	}

	// 찜 여부
	@Override
	public int likeCheck(int restaurantNo, int memberNo) {
		return mapper.likeCheck(restaurantNo, memberNo);
	}
	
	// 찜 추가
	@Override
	public int bookmarkadd(int restaurantNo, int memberNo) {
		return mapper.bookmarkadd(restaurantNo,memberNo);
	}
	
	// 찜 삭제
	@Override
	public int bookmarkdelete(int restaurantNo, int memberNo) {
		return mapper.bookmarkdelete(restaurantNo,memberNo);
	}
	
}
