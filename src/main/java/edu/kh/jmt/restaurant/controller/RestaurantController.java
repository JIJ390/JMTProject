package edu.kh.jmt.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.jmt.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("restaurant")
public class RestaurantController {
	
	private final RestaurantService service;
			
	@GetMapping("view")
	public String view() {
		return "restaurant/restaurantDetail";
	}
			
	@GetMapping("add")
	public String add() {
		return "restaurant/restaurantDetailAdd";
	}
	
	
}
