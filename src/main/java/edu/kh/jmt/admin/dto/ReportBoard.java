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
public class ReportBoard {

	private int reportBoardNo;
	private String reportBoardContent;
	private String reportBoardDate;
	private String reportBoardFeed;
	private String reportBoardFl;
	private String memberName;
	private int boardNo;
	private String reportTypeName;
	private String BoardDelFl;
	
	// 신고 대상 게시글을 쓴 작성자 번호
	private int BoardMemberNo;
}
