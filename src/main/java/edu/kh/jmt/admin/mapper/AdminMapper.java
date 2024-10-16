package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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

}
