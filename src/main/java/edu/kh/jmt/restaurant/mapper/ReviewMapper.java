package edu.kh.jmt.restaurant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;

@Mapper
public interface ReviewMapper {

	int imgRename(String renamePath);

	int reviewUpload(
			@Param("restaurantNo")int restaurantNo, 
			@Param("content")String content, 
			@Param("likeFl")String likeFl,
			@Param("memberNo")int memberNo);

	// 리뷰 리스트 조회
	List<ReviewDto> selectNewReview(@Param("restaurantNo")int restaurantNo, @Param("rowNum")int rowNum);

	List<ReviewDto> selectOldReview(@Param("restaurantNo")int restaurantNo, @Param("rowNum")int rowNum);

	int updateReview(
			@Param("reviewNo") int reviewNo, 
			@Param("content") String content, 
			@Param("likeFl")String likeFl);

	
	// 리뷰 삭제
	int reviewDelete(int reviewNo);

	// 리뷰 조회
	ReviewDto selectReview(int reviewNo);

	// 가게 조회
	RestaurantDto selectRestaurant(int restaurantNo);

	// 리뷰 수정
	int reviewUpdate(
			@Param("reviewNo")int reviewNo, 
			@Param("content")String content, 
			@Param("likeFl")String likeFl);

}
