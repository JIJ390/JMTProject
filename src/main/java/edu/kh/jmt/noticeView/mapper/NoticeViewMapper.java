package edu.kh.jmt.noticeView.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.noticeView.dto.Notice;

@Mapper
public interface NoticeViewMapper {

	/* 공지사항 게시글 확인 */
	int getListCount();

	/* 게시글  */
	List<Notice> selectNoticeList(RowBounds rowBounds);

}
