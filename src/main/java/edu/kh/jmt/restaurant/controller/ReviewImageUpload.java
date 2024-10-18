package edu.kh.jmt.restaurant.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
public class ReviewImageUpload {

	@ResponseBody
	@PostMapping("summerNoteUpload")
	public String postMethodName(
				@RequestParam("flie") MultipartFile file
				) {
		
		if (file.isEmpty()) {
			return "파일이 없습니다.";
		}

		
		
		// 이미지명 변경 후 저장된 url 리턴
		return "";
	}
}
