package edu.kh.jmt.common.scheduling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.common.scheduling.mapper.SchedulingMapper;

@Service
@Transactional
public class SchedulingServiceImpl implements SchedulingService{

	@Autowired
	private SchedulingMapper mapper;

	// db에서 모든 파일명 조회
	@Override
	public List<String> getDbfileNameList() {
		return mapper.getDbfileNameList();
	}
	
}
