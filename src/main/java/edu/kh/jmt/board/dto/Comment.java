package edu.kh.jmt.board.dto;

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
public class Comment {

	/* 테이블 컬럼 매핑필드 */
	private int commentNo;
	private String commentContent;
	private String commentDate;
	private String commentImg;
	private String commentDelFl;
	private int memberNo;
	private int boardNo;
	private int parentCommentNo;
	
	/* 댓글에 포함될 작성자이름, 프로필*/
	private String memberName; // 이름
	private String profileImg; // 프로필 이미지
	
	
	
}
