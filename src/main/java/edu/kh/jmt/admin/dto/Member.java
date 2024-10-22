package edu.kh.jmt.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Member {
	
	private int memberNo;
	private String memberName;
	private String memberEmail;
	private String memberPw;
	private String profileImg;
	private String memberDelFl;
	private String memberAuth;
	private String memberDate;

}
