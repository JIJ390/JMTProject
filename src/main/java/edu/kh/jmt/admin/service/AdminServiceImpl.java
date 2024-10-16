package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;
	
	@Override
	public List<Map<String, String>> selectCategoryList() {
		return mapper.selectCategoryList();
	}
	
	@Override
	public List<Map<String, String>> selectLocationList() {
		return mapper.selectLocationList();
	}
}
