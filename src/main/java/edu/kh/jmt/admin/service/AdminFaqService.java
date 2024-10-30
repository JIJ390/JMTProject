package edu.kh.jmt.admin.service;

import java.util.Map;

import edu.kh.jmt.noticeView.dto.Faq;
import edu.kh.jmt.noticeView.dto.Notice;

public interface AdminFaqService {

	/**
	 * FAQ 전체 리스트 불러오기
	 * 
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectFaqList(int cp);

	
		/**
		 * FAQ 삭제
		 * 
		 * @param faqNo
		 * @return
		 */

	  int deleteFaq(int faqNo);


	/**
	 * FAQ 신규 작성 
	 * @param faq
	 * @return
	 */
	int insertFaq(Faq faq);


	/**
	 * FAQ 수정 페이지
	 * @param faqNo
	 * @return
	 */
	Faq updateFaqView(int faqNo);


	/**
	 * FAQ 수정하기
	 * @param faqNo
	 * @return
	 */
	int updateFaq(Faq faq);
	


}
