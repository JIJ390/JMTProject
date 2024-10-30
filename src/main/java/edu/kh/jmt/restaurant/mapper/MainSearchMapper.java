package edu.kh.jmt.restaurant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

@Mapper
public interface MainSearchMapper {

	/** 검색 기능
	 * 
	 * @param searchCode
	 * @param region 지역
	 * @param tag 음식
	 * @param memberNo 
	 * @return
	 */
	List<RestaurantDto> searchResult(@Param ("searchCode") String searchCode, 
									 @Param ("tag") String tag, 
									 @Param ("region") String region, 
									 @Param ("memberNo") int memberNo);

	// 최신순
	List<RestaurantDto> latest(@Param ("searchCode") String searchCode, 
					  	       @Param ("tag") String tag, 
					  	       @Param ("region") String region, 
					  	       @Param ("memberNo") int memberNo);
	//찜 믾은 순
	List<RestaurantDto> likeOrder(@Param ("searchCode") String searchCode, 
			 					  @Param ("tag") String tag, 
			 					  @Param ("region") String region, 
			 					  @Param ("memberNo") int memberNo);


	
	
	
	
	
}
