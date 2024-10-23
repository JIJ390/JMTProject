package edu.kh.jmt.restaurant.service;

import org.springframework.web.multipart.MultipartFile;

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

}
