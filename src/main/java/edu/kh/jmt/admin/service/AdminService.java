package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

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

}
