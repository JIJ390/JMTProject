package edu.kh.jmt.restaurant.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.restaurant.dto.ReviewDto;

public interface ReviewService {

	/**
	 * summerNote 이미지 rename 후 파일 저장
	 * @param file
	 * @return
	 */
	String imgRename(MultipartFile file);

	/**
	 * 리뷰 업로드
	 * @param restaurantNo
	 * @param content
	 * @param likeFl
	 * @return
	 */
	int reviewUpload(int restaurantNo, String content, String likeFl, int memberNo);

	/**
	 * 리뷰 리스트 조회
	 * @param restaurantNo
	 * @param rowNum
	 * @return
	 */
	List<ReviewDto> selectReview(int restaurantNo, int rowNum, int sort);

}
