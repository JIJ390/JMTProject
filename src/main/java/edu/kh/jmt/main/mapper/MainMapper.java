package edu.kh.jmt.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.restaurant.dto.RestaurantDto;

@Mapper
public interface MainMapper {

	/** 메인페이지 좋아요 순
	 * 
	 * @return
	 */
	List<RestaurantDto> listLike();
	
	/** 메인페이지 최신 순
	 * 
	 * @return
	 */
	List<RestaurantDto> listCurrent();
	/** 리뷰 많은 순
	 * 
	 * @return
	 */
	List<RestaurantDto> listReview();


	
	
}
