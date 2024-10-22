package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Member;
import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Restaurant;

public interface AdminService {

	/**
	 * 카테고리 리스트 인터셉터로 찾아오기
	 * @return
	 */
	List<Map<String, String>> selectCategoryList();

	/**
	 * 지역 리스트 찾기
	 * @return
	 */
	List<Map<String, String>> selectLocationList();

	/**
	 * 가게 정보 등록
	 * @param insertRestaurant
	 * @param restaurantImges
	 * @return
	 */
	int restaurantInsert(
			Restaurant insertRestaurant, 
			List<MultipartFile> restaurantImages,
			List<String> menuNameList,
			List<String> menuPriceList);

	
	/**
	 * 회원 정보 모두 불러오기
	 * @param condition 
	 * @return
	 */
	Map<String, Object> selectMemberList(Map<String, String> condition);

	
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
	 * 회원 탈퇴 여부 변경
	 * @param memberNo
	 * @return
	 */
	int changeMemberSecession(int memberNo);

	
	
	/**
	 * 임시 로그인
	 * @param memberNo
	 * @return
	 */
	Member directLogin(int memberNo);


}
