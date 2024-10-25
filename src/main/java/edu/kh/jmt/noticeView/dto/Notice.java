package edu.kh.jmt.noticeView.dto;

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
@Builder
@ToString
public class Notice {

	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriteDate;
	private String noticeDelFl;
	private int noticeReadCount;
	private String noticeCategory;
	private String max;
}
