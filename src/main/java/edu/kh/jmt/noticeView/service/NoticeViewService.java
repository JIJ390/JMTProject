package edu.kh.jmt.noticeView.service;

import java.util.List;
import java.util.Map;

import edu.kh.jmt.noticeView.dto.Faq;

public interface NoticeViewService {

	/* 공지사항 불러오기 */
	Map<String, Object> selectNoticeList(int cp);

	/* FAQ 리스트 불러오기 */
	List<Faq> selectFaqList();



}
