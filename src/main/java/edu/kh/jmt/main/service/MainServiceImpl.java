package edu.kh.jmt.main.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import edu.kh.jmt.main.mapper.MainMapper;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;
	

//인기순 SQL
	@Override
	public List<RestaurantDto> listLike(){
		return mapper.listLike();
	}
	
	// 최신순
	@Override
	public List<RestaurantDto> listCurrent() {
		return mapper.listCurrent();
	}
	@Override
	public List<RestaurantDto> listReview() {
		return mapper.listReview();
	}
	
}
