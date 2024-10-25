package edu.kh.jmt.restaurant.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.common.util.FileUtil;
import edu.kh.jmt.restaurant.dto.ReviewDto;
import edu.kh.jmt.restaurant.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;

@PropertySource("classpath:/config.properties")
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewMapper mapper;
	
	@Value("${my.review.web-path}")
	private String webPath;

	@Value("${my.review.folder-path}")
	private String folderPath;

	@Override
	public String imgRename(MultipartFile file) {
		
		File folder = new File(folderPath);
		
		if(folder.exists() == false) {
			folder.mkdirs();
		}
			
		String rename = FileUtil.rename(file.getOriginalFilename());
		
		String renamePath = folderPath + rename;
		
		int result = mapper.imgRename(renamePath);
		
		try {
			if(result > 0) {
				file.transferTo(new File(folderPath + rename));
			}
		}catch (Exception e) {
			new RuntimeException("파일 저장 실패");
		}
		
		return webPath + rename;
	}

	@Override
	public int reviewUpload(int restaurantNo, String content, String likeFl, int memberNo) {
		return mapper.reviewUpload(restaurantNo, content, likeFl, memberNo);
	}

	// 리뷰 리스트 조회
	@Override
	public List<ReviewDto> selectReview(int restaurantNo, int rowNum, int sort) {
		
		if(sort == 1) {
			return mapper.selectNewReview(restaurantNo, rowNum);
		}
		else {
			return mapper.selectOldReview(restaurantNo, rowNum);
		}
				
	}
	
}
