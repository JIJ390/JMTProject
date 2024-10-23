package edu.kh.jmt.noticeView.service;

import java.util.Map;

public interface NoticeViewService {

	/* 공지사항 불러오기 */
	Map<String, Object> selectNoticeList(int cp);

}
