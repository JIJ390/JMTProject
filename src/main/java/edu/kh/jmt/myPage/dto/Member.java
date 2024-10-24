package edu.kh.jmt.myPage.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Member {

	private int memberNo; // 회원번호
  private String memberName; // 이름
  private String memberEmail; // 이메일
  private String memberPw; // 비밀번호
  private MultipartFile profileImg; // 프로필 이미지
  private String memberDelFl; // 회원 탈퇴 여부
  private int memberAuth; // 권한
  private String memberDate; // 가입일
	
}
