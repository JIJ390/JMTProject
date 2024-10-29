
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

// 수정 버튼 클릭
const insertBtn = document.querySelector("#insertBtn");

insertBtn.addEventListener("click", e => {
    // 서버로 제출되어야 하는 값을 JS 객체 형태로 묶기
    const obj = {}; // 빈 객체 생성
    obj.todoNo = updateBtn.dataset.todoNo; // 버튼에 dataset 값 얻어오기
  
    obj.todoSub = document.querySelector("#updateTitle").value;
    obj.todoContent = document.querySelector("#updateContent").value;
  
    console.log(obj);
  
    // 비동기로 할일 수정 요청
    fetch("/admin/faqEdit", {
      method : "PUT",
      headers : {"Content-Type" : "application/json"},
      body : JSON.stringify(obj)
    })
    .then(response => {
      if(response.ok) return response.text();
      throw new Error("수정 실패 : " + response.status);
    })
    .then(result => {
      console.log(result); // 1 or 0
  
      if(result > 0){
        // 수정 성공
        alert("수정 성공!!");  
      } else {
        // 수정 실패
        alert("수정 실패 ㅠㅠ");
      }
    })
    .catch( err => console.error(err) );
})