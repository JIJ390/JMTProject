package edu.kh.jmt.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.noticeView.dto.Faq;

@Mapper
public interface AdminFaqMapper {

	/* 개수 확인 */
	int getListCount();

	/* FAQ 리스트 부르기 */
	List<Faq> selectFaqList(RowBounds rowBounds);

	/* FAQ 삭제 */
	int deleteFaq(int faqNo);

	/* FAQ 등록 */
	int insertFaq(Faq faq);

	/* FAQ 수정 페이지 진입 */
	Faq updateFaqView(int faqNo);

	/* FAQ 수정하기 */
	int updateFaq(Faq faq);

}
