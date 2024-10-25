package edu.kh.jmt.restaurant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
