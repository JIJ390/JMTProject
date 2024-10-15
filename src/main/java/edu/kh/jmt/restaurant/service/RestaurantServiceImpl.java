package edu.kh.jmt.restaurant.service;

import org.springframework.stereotype.Service;

import edu.kh.jmt.restaurant.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{
	
	private final RestaurantMapper mapper;
	
}
