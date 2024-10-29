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
	 * 검색 시 공지 사항 목록 불러오기
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> searchNoticeList(int cp, Map<String, Object> paramMap);


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

	
	/**
	 * 공지사항 수정
	 * @param notice
	 * @return
	 */
	int updateNotice(Notice notice);

}
