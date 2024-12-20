package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.noticeView.dto.Notice;

@Mapper
public interface AdminNoticeMapper {

	/**
	 * 개수 파악
	 * @return
	 */
	int getListCount();

	
	/**
	 * 전체 공지 목록 가져오기
	 * @param rowBounds
	 * @return
	 */
	List<Notice> selectNoticeList(RowBounds rowBounds);

	
	/**
	 * 검색시 공지 숫자 카운트
	 * @param paramMap
	 * @return
	 */
	int searchNoticeCount(Map<String, Object> paramMap);

	
	/**
	 * 검색 시 공지 목록 가져오기
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Notice> searchNoticeList(Map<String, Object> paramMap, RowBounds rowBounds);

	
	
	/**
	 * 공지 삭제 처리
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
	 * 공지 수정 페이지 이동
	 * @param noticeNo : 수정하려는 공지 번호
	 * @return notice
	 */
	Notice updateNoticeView(int noticeNo);


	/**
	 * 공지 수정
	 * @param notice
	 * @return
	 */
	int updateNotice(Notice notice);





	

}
