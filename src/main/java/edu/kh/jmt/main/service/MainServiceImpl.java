package edu.kh.jmt.main.service;

import org.springframework.stereotype.Service;

import edu.kh.jmt.main.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;
	

	/** 화면전환 검색 값 보내기
	 * 
	 */
	@Override
	public String searchPage(String searchBar) {
		
		return mapper.searchPage(searchBar);
	}

	
}
