package edu.kh.jmt.admin.service;

import java.util.Map;

import edu.kh.jmt.noticeView.dto.Notice;

public interface AdminNoticeService {

	/**
	 * 공지 사항 리스트 불러오기
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectNoticeList(int cp);

	/**
	 * 공지 삭제
	 * @param noticeNo
	 * @return
	 */
	int deleteNotice(int noticeNo);

	
	/**
	 * 공지 등록
	 * @param notice
	 * @return
	 */
	int insertNotice(Notice notice);

	
	/**
	 * 공지 사항 수정 페이지 이동
	 * @param string
	 * @return
	 */
	Notice updateNoticeView(int noticeNo);

	int updateNotice(int noticeNo);

}
