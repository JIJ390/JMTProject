package edu.kh.jmt.common.scheduling.service;

import java.util.List;

public interface SchedulingService {

	/**
	 * db에 기록된 모든 파일명 조회
	 * @return list
	 */
	List<String> getDbfileNameList();

}
