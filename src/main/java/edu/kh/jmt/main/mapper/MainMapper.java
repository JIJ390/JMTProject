package edu.kh.jmt.main.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	/** 화면전환 검색 값 보내기
	 * 
	 * @param searchBar
	 * @return
	 */
	String searchPage(String searchBar);

	
	
}
