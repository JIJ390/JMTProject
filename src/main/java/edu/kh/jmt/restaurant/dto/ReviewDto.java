package edu.kh.jmt.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ReviewDto {
	private int reviewNo;
	private String reviewContent;
	private String likeFl;
	private String reviewDate;
	private String reviewDelFl;
	private int restaurantNo;
	private int memberNo;
	
	private String profileImg;
	private String memberName;
}
