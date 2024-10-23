package edu.kh.jmt.restaurant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {

	int imgRename(String renamePath);

	int reviewUpload(
			@Param("restaurantNo")int restaurantNo, 
			@Param("content")String content, 
			@Param("likeFl")String likeFl,
			@Param("memberNo")int memberNo);


}
