package edu.kh.jmt.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.jmt.myPage.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageServiceImpl service;
}
