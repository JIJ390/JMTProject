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
public class ReportComment {

	
	private int reportCommentNo;
	private String reportCommentContent;
	private String reportCommentDate;
	private String reportCommentFeed;
	private String reportCommentFl;
	private String memberName;
	private int commentNo;
	private String reportTypeName;
	private String CommentDelFl;
	
	// 신고 대상 게시글을 쓴 작성자 번호
	private int commentMemberNo;
}
