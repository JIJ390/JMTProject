package edu.kh.jmt.restaurant.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.common.util.FileUtil;
import edu.kh.jmt.restaurant.dto.RestaurantDto;
import edu.kh.jmt.restaurant.dto.ReviewDto;
import edu.kh.jmt.restaurant.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;

@PropertySource("classpath:/config.properties")
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper mapper;

	@Value("${my.review.web-path}")
	private String webPath;

	@Value("${my.review.folder-path}")
	private String folderPath;

	@Override
	public String imgRename(MultipartFile file) {

		File folder = new File(folderPath);

		if (folder.exists() == false) {
			folder.mkdirs();
		}

		String rename = FileUtil.rename(file.getOriginalFilename());

		String renamePath = folderPath + rename;

		int result = mapper.imgRename(renamePath);

		try {
			if (result > 0) {
				file.transferTo(new File(folderPath + rename));
			}
		} catch (Exception e) {
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

		
 		if (sort == 1) {
			return mapper.selectNewReview(restaurantNo, rowNum);
		} else {
			return mapper.selectOldReview(restaurantNo, rowNum);
		}

	}

	// 리뷰 수정
	@Override
	public int updateReview(int reviewNo, String content, String likeFl) {
		return mapper.updateReview(reviewNo, content, likeFl);
	}

	// 리뷰 삭제
	@Override
	public int reviewDelete(int reviewNo) {
		return mapper.reviewDelete(reviewNo);
	}

	// 리뷰 조회
	@Override
	public ReviewDto selectReview(int reviewNo) {
		return mapper.selectReview(reviewNo);
	}

	// 가게 조회
	@Override
	public RestaurantDto selectRestaurant(int restaurantNo) {
		return mapper.selectRestaurant(restaurantNo);
	}

	@Override
	public int reviewUpdate(int reviewNo, String content, String likeFl) {
		return mapper.reviewUpdate(reviewNo, content, likeFl);
	}

	// 리뷰 신고
	@Override
	public int reportAdd(String reportType, String reportContent, int reviewNo, int memberNo) {

		if (reportType.equals("악의적인 리뷰")) {
			reportType = "1";
		} else if (reportType.equals("선정적인 리뷰")) {
			reportType = "2";
		} else if (reportType.equals("부적절한 리뷰")) {
			reportType = "3";
		} else {
			reportType = "4";
		}
		
		if(reportContent.equals("")) {
			reportContent = "리뷰 신고";
		}

		if(mapper.selectReportReview(memberNo,reviewNo,reportType) > 0) {
			return -1;
		}
		int result = mapper.reportAdd(reportType, reportContent, reviewNo, memberNo);

		return result;
	}
	
	
	@Override
	public int reviewSize(int restaurantNo) {
		int size = mapper.selectReviewSize(restaurantNo);
		return size;
	}
	
	
	
	
	
	
}
