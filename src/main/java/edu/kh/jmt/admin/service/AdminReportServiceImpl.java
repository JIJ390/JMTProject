package edu.kh.jmt.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.dto.ReportBoard;
import edu.kh.jmt.admin.dto.ReportComment;
import edu.kh.jmt.admin.dto.ReportReview;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.mapper.AdminReportMapper;
import edu.kh.jmt.myPage.dto.Member;
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
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	// 리뷰 신고 상세 조회
	@Override
	public Map<String, Object> reportReviewDetail(int reportReviewNo) {
		
		// 리뷰 정보 조회
		ReportReview reportReview = mapper.reportReviewDetail(reportReviewNo);
		
		Member member = mapper.selectReviewMember(reportReview.getReviewMemberNo());
		
		Map<String, Object> map = new HashMap<>();
		
		map.put(("reportReview"), reportReview);
		map.put(("member"), member);	
		
		return map;
	}
	
	// 리뷰 신고 처리
	@Override
	public int reportReviewFeed(ReportReview reportReview) {
		return mapper.reportReviewFeed(reportReview);
	}
	
	
	// 리뷰 삭제 처리
	@Override
	public int reportReviewDelete(int reviewNo) {
		return mapper.reportReviewDelete(reviewNo);
	}
	
	
	
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////

	// 게시글 신고
	
//게시글 신고 목록 가져오기 - 일반
	@Override
	public Map<String, Object> selectReportBoardList(int cp) {
		
		int reportBoardCount = mapper.getReportBoardCount();
		
		AdminPagination adminPagination = new AdminPagination(cp, reportBoardCount);
		
		int limit = adminPagination.getLimit(); 	// 10
		int offset = (cp - 1) * limit;			// 0
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<ReportBoard> reportBoardList = mapper.selectReportBoardList(rowBounds);
		
		// 4. 목록 조회 결과 + Pagination 객체 Map 으로 묶어서 반환
		Map<String, Object> map = new HashMap<>();
		map.put(("reportBoardList"), reportBoardList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	
	
	// 게시글 신고 목록 가져오기 - 검색
	@Override
	public Map<String, Object> searchReportBoardList(int cp, Map<String, Object> paramMap) {
		
		int searchCount = mapper.searchReportBoardCount(paramMap);

		
		// 2. Pagination 객체 생성하기
		AdminPagination adminPagination = new AdminPagination(cp, searchCount);
		
		
		//3. DB에서 cp(조회 하려는 페이지)에 해당하는 행을 조회
		int limit = adminPagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		// 4. 검색 결과 + Pagination 객체 Map 으로 묶어서 반환
		List<ReportBoard> reportBoardList = mapper.searchReportBoardList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put(("reportBoardList"), reportBoardList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	// 게시글 신고 상세 조회
	@Override
	public Map<String, Object> reportBoardDetail(int reportBoardNo) {
		
		// 게시글 정보 조회
		ReportBoard reportBoard = mapper.reportBoardDetail(reportBoardNo);
		
		Member member = mapper.selectBoardMember(reportBoard.getBoardMemberNo());
		
		Map<String, Object> map = new HashMap<>();
		
		map.put(("reportBoard"), reportBoard);
		map.put(("member"), member);	
		
		return map;
	}
	
	// 게시글 신고 처리
	@Override
	public int reportBoardFeed(ReportBoard reportBoard) {
		return mapper.reportBoardFeed(reportBoard);
	}
	
	
	// 게시글 삭제 처리
	@Override
	public int reportBoardDelete(int boardNo) {
		return mapper.reportBoardDelete(boardNo);
	}
	
	
	
	
	
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////

	// 댓글 신고
	
//댓글 신고 목록 가져오기 - 일반
	@Override
	public Map<String, Object> selectReportCommentList(int cp) {
		
		int reportCommentCount = mapper.getReportCommentCount();
		
		AdminPagination adminPagination = new AdminPagination(cp, reportCommentCount);
		
		int limit = adminPagination.getLimit(); 	// 10
		int offset = (cp - 1) * limit;			// 0
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<ReportComment> reportCommentList = mapper.selectReportCommentList(rowBounds);
		
		// 4. 목록 조회 결과 + Pagination 객체 Map 으로 묶어서 반환
		Map<String, Object> map = new HashMap<>();
		map.put(("reportCommentList"), reportCommentList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	
	
	// 댓글 신고 목록 가져오기 - 검색
	@Override
	public Map<String, Object> searchReportCommentList(int cp, Map<String, Object> paramMap) {
		
		int searchCount = mapper.searchReportCommentCount(paramMap);

		
		// 2. Pagination 객체 생성하기
		AdminPagination adminPagination = new AdminPagination(cp, searchCount);
		
		
		//3. DB에서 cp(조회 하려는 페이지)에 해당하는 행을 조회
		int limit = adminPagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		// 4. 검색 결과 + Pagination 객체 Map 으로 묶어서 반환
		List<ReportComment> reportCommentList = mapper.searchReportCommentList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put(("reportCommentList"), reportCommentList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	// 댓글 신고 상세 조회
	@Override
	public Map<String, Object> reportCommentDetail(int reportCommentNo) {
		
		// 댓글 정보 조회
		ReportComment reportComment = mapper.reportCommentDetail(reportCommentNo);
		
		Member member = mapper.selectCommentMember(reportComment.getCommentMemberNo());
		
		Map<String, Object> map = new HashMap<>();
		
		map.put(("reportComment"), reportComment);
		map.put(("member"), member);	
		
		return map;
	}
	
	// 댓글 신고 처리
	@Override
	public int reportCommentFeed(ReportComment reportComment) {
		return mapper.reportCommentFeed(reportComment);
	}
	
	
	// 댓글 삭제 처리
	@Override
	public int reportCommentDelete(int reviewNo) {
		return mapper.reportCommentDelete(reviewNo);
	}
	
	
}
