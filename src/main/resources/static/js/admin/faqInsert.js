
// 간단한 유효성 검사만 존재하는 페이지이므로 faqUpdate 와 공유함

// 공지 등록 폼 요소 가져오기
const noticeInsertFrm = document.querySelector("#faqInsertFrm");


faqInsertFrm.addEventListener("submit", e => {

  const faqTitle = document.querySelector("[name=faqTitle]");
  const faqContent = document.querySelector("[name=faqContent]");

  // 제목 유효성 검사
  if (faqTitle.value.trim().length < 3) {
    alert("제목을 2글자 이상으로 작성해 주세요");
    faqTitle.focus();
    e.preventDefault();
    return;
  }
 

  if (faqContent)

    // 내용 유효성 검사
  if (faqContent.value.trim().length < 3) {
    alert("FAQ 내용을 2글자 이상으로 작성해 주세요");
    faqContent.focus();
    e.preventDefault();
    return;
  }


});

