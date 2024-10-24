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
public class Faq {
	private int faqNo;
	private String faqTitle;
	private String faqContent;
	private String faqDelFl;
}
