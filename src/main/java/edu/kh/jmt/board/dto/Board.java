package edu.kh.jmt.board.dto;

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
public class Board {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private String boardImg;
	private String boardDelFl;
	private int memberNo;
	
	private String memberName;
	
}
