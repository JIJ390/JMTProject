
// 간단한 유효성 검사만 존재하는 페이지이므로 noticeUpdate 와 공유함

// 공지 등록 폼 요소 가져오기
const noticeInsertFrm = document.querySelector("#noticeInsertFrm");


noticeInsertFrm.addEventListener("submit", e => {

  const noticeTitle = document.querySelector("[name=noticeTitle]");
  const noticeContent = document.querySelector("[name=noticeContent]");
  const noticeCategory = document.querySelectorAll("[name=noticeCategory]");

  // 제목 유효성 검사
  if (noticeTitle.value.trim().length < 3) {
    alert("제목을 2글자 이상으로 작성해 주세요");
    noticeTitle.focus();
    e.preventDefault();
    return;
  }

    // 체크되었는지 확인하기 위한 flag 변수
    let flag = true;

    // 공지 카테고리 선택
    for (let i=0; i < noticeCategory.length; i++) {
      // input 이 체크된 경우가 있을 때 false
      if (noticeCategory[i].checked) flag = false;
    }
  
    if (flag) {
      alert("공지 카테고리를 선택해 주세요");
      e.preventDefault();
      return;
    }
  

  if (noticeContent)


    // 내용 유효성 검사
  if (noticeContent.value.trim().length < 3) {
    alert("공지 내용을 2글자 이상으로 작성해 주세요");
    noticeContent.focus();
    e.preventDefault();
    return;
  }


});