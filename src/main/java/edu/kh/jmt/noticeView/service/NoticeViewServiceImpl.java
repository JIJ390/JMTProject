package edu.kh.jmt.noticeView.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.jmt.noticeView.dto.Notice;
import edu.kh.jmt.noticeView.dto.NoticePagination;
import edu.kh.jmt.noticeView.mapper.NoticeViewMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeViewServiceImpl implements NoticeViewService {
	
	private final NoticeViewMapper mapper;
	
	
	/**
	 * 공지사항 불러오기
	 */
	@Override
	public Map<String, Object> selectNoticeList(int cp) {
		
		// 1. 작성된 공지사항 게시글 확인
		int listCount = mapper.getListCount();
		
		// 2. listCount와 cp 를 이용해서
			// 조회될 목록 페이지, 출력할 페이징(pageNation) 값을 계산할
			// Pagination 객체 생성하기 
			
			NoticePagination pagination = new NoticePagination(cp, listCount);
			int limit = pagination.getLimit();
			int offset = (cp - 1)* limit;
			
			RowBounds rowBounds = new RowBounds(offset, limit);
			
			List<Notice> noticeList = mapper.selectNoticeList(rowBounds);
			
			// 목록 조회 결과 + Pagination 객체 Map 묶어서 반환 
			Map<String, Object> map = 
					Map.of("noticeList", noticeList, "pagination", pagination);
				
			
		return map;
	}

}
