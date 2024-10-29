package edu.kh.jmt.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	
	/** 찜하기
	 * 
	 * @param memberNo
	 * @param restaurantNo
	 * @return result 0,1
	 */
	int storeLike(
					@Param("memberNo") int memberNo, 
					@Param("restaurantNo") int restaurantNo);
	//찜하기 지우기
	int deleteLike(
					@Param("memberNo")int memberNo, 
					@Param("restaurantNo") int restaurantNo);
	//찜하기
	int insertLike(
					@Param("memberNo")int memberNo, 
					@Param("restaurantNo") int restaurantNo);
	

	
	
}
