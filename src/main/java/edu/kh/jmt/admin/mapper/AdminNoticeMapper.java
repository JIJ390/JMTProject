package edu.kh.jmt.admin.mapper;

import java.util.List;

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

}
