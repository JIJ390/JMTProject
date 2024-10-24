package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Restaurant;

@Mapper
public interface AdminRestaurantMapper {

	/**
	 * 카테고리 리스트 불러오기
	 * @return
	 */
	List<Map<String, String>> selectCategoryList();

	/**
	 * 지역 리스트 불러오기
	 * @return
	 */
	List<Map<String, String>> selectLocationList();
	
	
	/**
	 * 폐업하지 않은 가게 전체 수 조회
	 * @return
	 */
	int getRestaurantListCount();
	
	
	/**
	 * 검색 아닌 경우 전체 가게 리스트 조회
	 * @param rowBounds
	 * @return restaurantList
	 */
	List<Restaurant> selectRestaurantList(RowBounds rowBounds);
	
	/**
	 * 검색 조건에 맞는 전체 가게 수 조회
	 * @param paramMap
	 * @return
	 */
	int getSearchCount(Map<String, Object> paramMap);
	
	/**
	 * 검색 조건에 맞는 전체 가게 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return restaurantList
	 */
	List<Restaurant> restaurantSearchList(Map<String, Object> paramMap, RowBounds rowBounds);
	
	
	/**
	 * 가게 정보 등록
	 * @param insertRestaurant
	 * @return restaurantNo
	 */
	int restaurantInsert(Restaurant insertRestaurant);

	/**
	 * 메뉴 정보 등록
	 * @param menuList
	 * @param restaurantNo	: 해당 메뉴가 등록된 가게 번호
	 * @return
	 */
	int menuListInsert(List<Menu> menuList);



	/**
	 * 특정 가게 정보 상세 조회
	 * @param restaurantNo
	 * @return Restaurant
	 */
	Restaurant selectRestaurant(int restaurantNo);

	
	/**
	 * 특정 가게 메뉴 정보 조회
	 * @param restaurantNo
	 * @return menuList
	 */
	List<Menu> selectMenuList(int restaurantNo);

	
	/**
	 * 가게 정보 삭제
	 * @param restaurantNo
	 * @return
	 */
	int restaurantDelete(int restaurantNo);

	/**
	 * 가게 수정용 select sql
	 * 카테고리 코드와 전화번호 일반 형식으로 조회
	 * @param restaurantNo
	 * @return
	 */
	Restaurant restaurantUpdateView(int restaurantNo);

	/**
	 * 가게 수정용 메뉴리스트
	 * @param restaurantNo
	 * @return
	 */
	List<Menu> menuUpdateView(int restaurantNo);
	
	

	
	
	


}
