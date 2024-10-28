package edu.kh.jmt.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportReview {

	private int reportReviewNo;
	private String reportReviewContent;
	private String reportReviewDate;
	private String reportReviewFeed;
	private String reportReviewFl;
	private String memberName;
	private int reviewNo;
	private String reportTypeName;
	private String reviewDelFl;
	
	
	// 신고 대상 리뷰 가게 번호
	private int	restaurantNo;
	
	// 신고 대상 리뷰를 쓴 작성자 번호
	private int reviewMemberNo;
	
}
