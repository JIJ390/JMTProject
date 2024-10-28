package edu.kh.jmt.noticeView.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.noticeView.dto.Faq;
import edu.kh.jmt.noticeView.dto.Notice;

@Mapper
public interface NoticeViewMapper {

	/* 공지사항 게시글 확인 */
	int getListCount();

	/* 공지 게시글 전체  */
	List<Notice> selectNoticeList(RowBounds rowBounds);

	/* FAQ 전체 리스트 */
	List<Faq> selectFaqList();

	/* 공지사항 자세히 보기 */
	Notice detailViewNotice(int noticeNo);

}
