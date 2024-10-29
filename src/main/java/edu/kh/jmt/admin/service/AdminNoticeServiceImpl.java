package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.mapper.AdminNoticeMapper;
import edu.kh.jmt.noticeView.dto.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminNoticeServiceImpl implements AdminNoticeService{
	
	private final AdminNoticeMapper mapper;

	// 공지 사항 불러오기
	@Override
	public Map<String, Object> selectNoticeList(int cp) {
		int listCount = mapper.getListCount();
			
			AdminPagination pagination = new AdminPagination(cp, listCount);
			
			int limit = pagination.getLimit();
			int offset = (cp - 1)* limit;
			
			RowBounds rowBounds = new RowBounds(offset, limit);
			
			List<Notice> noticeList = mapper.selectNoticeList(rowBounds);
			
			Map<String, Object> map = 
					Map.of("noticeList", noticeList, "pagination", pagination);
				
		return map;
	}
	
	
	
	// 공지 삭제
	@Override
	public int deleteNotice(int noticeNo) {
		return mapper.deleteNotice(noticeNo);
	}
	
	// 공지 등록
	@Override
	public int insertNotice(Notice notice) {
		return mapper.insertNotice(notice);
	}
	
	// 공지 수정 페이지 이동
	@Override
	public Notice updateNoticeView(int noticeNo) {
		return mapper.updateNoticeView(noticeNo);
	}
}
