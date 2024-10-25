package edu.kh.jmt.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.mapper.AdminReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminReportServiceImpl implements AdminReportService{

	private final AdminReportMapper mapper;
	
	
	// 리뷰 신고 목록 가져오기 - 일반
	@Override
	public Map<String, Object> selectReportReviewList(int cp) {
		
		int reportReviewCount = mapper.getReportReviewCount();
		
		AdminPagination adminPagination = new AdminPagination(cp, reportReviewCount);
		
		int limit = adminPagination.getLimit(); 	// 10
		int offset = (cp - 1) * limit;			// 0
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<ReportReview> reportReviewList = mapper.selectReportReviewList(rowBounds);
		
		// 4. 목록 조회 결과 + Pagination 객체 Map 으로 묶어서 반환
		Map<String, Object> map = new HashMap<>();
		map.put(("reportReviewList"), reportReviewList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	
	
	// 리뷰 신고 목록 가져오기 - 검색
	@Override
	public Map<String, Object> searchReportReviewList(int cp, Map<String, Object> paramMap) {
		
		int searchCount = mapper.searchReportReviewCount(paramMap);

		
		// 2. Pagination 객체 생성하기
		AdminPagination adminPagination = new AdminPagination(cp, searchCount);
		
		
		//3. DB에서 cp(조회 하려는 페이지)에 해당하는 행을 조회
		int limit = adminPagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		// 4. 검색 결과 + Pagination 객체 Map 으로 묶어서 반환
		List<ReportReview> reportReviewList = mapper.searchReportReviewList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put(("reportReviewList"), reportReviewList);
		map.put(("pagination"), adminPagination);		// 3 줄
		
		return map;
	}
}
