package edu.kh.jmt.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.mapper.AdminFaqMapper;
import edu.kh.jmt.noticeView.dto.Faq;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminFaqServiceImpl implements AdminFaqService{
	private final AdminFaqMapper mapper;
	
	
	//FAQ 불러오기
	@Override
	public Map<String, Object> selectFaqList(int cp) {
		int listCount = mapper.getListCount();
			
			AdminPagination pagination = new AdminPagination(cp, listCount);
			
			int limit = pagination.getLimit();
			int offset = (cp - 1)* limit;
			
			RowBounds rowBounds = new RowBounds(offset, limit);
			
			List<Faq> faqList = mapper.selectFaqList(rowBounds);
			
			Map<String, Object> map = 
					Map.of("faqList", faqList, "pagination", pagination);
				
		return map;
	}
	
	/**
	 * FAQ 삭제
	 */
	@Override
	public int deleteFaq(int faqNo) {
		return mapper.deleteFaq(faqNo);
	}
	
	
	/**
	 * FAQ 신규 작성
	 */
	@Override
	public int insertFaq(Faq faq) {
		return mapper.insertFaq(faq);
	}
	
	
	/**
	 * FAQ 수정 페이지
	 */
	@Override
	public Faq updateFaqView(int faqNo) {
		return mapper.updateFaqView(faqNo);
	}
	
	
	/**
	 * FAQ 수정
	 */
	@Override
	public int updateFaq(Faq faq) {
		return mapper.updateFaq(faq);
	}
	
	
} // end