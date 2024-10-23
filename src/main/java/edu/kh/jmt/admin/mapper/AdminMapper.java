package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Restaurant;
import io.lettuce.core.dynamic.annotation.Param;
import edu.kh.jmt.admin.dto.Member;

@Mapper
public interface AdminMapper {

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


	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////
	// 회원 관리 페이지
	
	
	
	
	/** 
	 * 검색 조건에 맞는 회원 정보 모두 조회
	 * @param condition 
	 * @param rowBounds 
	 * @return
	 */
	List<Member> selectMemberList(Map<String, String> condition, RowBounds rowBounds);

	
	
	/**
	 * 회원 현황 조회
	 * @return
	 */
	Map<String, String> selectMemberStatus();

	
	/**
	 * 회원 차단 여부 변경
	 * @param memberNo
	 * @return
	 */
	int changeMemberBlock(int memberNo);

	
	/**
	 * 회원 탈퇴 상태 변경
	 * @param memberNo
	 * @return
	 */
	int changeMemberSecession(int memberNo);

	
	/**
	 * 검색 조건에 맞는 총 회원 수 조회
	 * @param condition
	 * @return
	 */
	int getMemberSearchCount(Map<String, String> condition);

	
	/**
	 * 임시 로그인 삭제 예정
	 * @param memberNo
	 * @return
	 */
	Member directLogin(int memberNo);








	
	
	


}
