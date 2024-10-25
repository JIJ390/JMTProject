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
	
}
