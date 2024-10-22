package edu.kh.jmt.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriteDate;
	private String noticeDelFl;
	private int noticeReadCount;
	private String noticeCategory;
}
