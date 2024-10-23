package edu.kh.jmt.common.scheduling.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulingMapper {

	/**
	 * db에서 모든 파일명 조회
	 * @return list
	 */
	List<String> getDbfileNameList();

}
