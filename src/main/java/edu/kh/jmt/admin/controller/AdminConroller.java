package edu.kh.jmt.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminConroller {

	@GetMapping("restaurant/regist")
	public String restaurantRegist() {
		return "admin/restaurantRegist";
	}
	
	
	@GetMapping("restaurant/manage")
	public String restaurantManage() {
		
		return "admin/restaurantManage";
	}
}
