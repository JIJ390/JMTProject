

// 페이지 이동을 위한 버튼 모두 얻어오기
const pageNoList = document.querySelectorAll(".pagination a");

// 페이지 이동 버튼이 클릭 되었을 때
pageNoList?.forEach((item, index) => {

  // 클릭 되었을 때
  item.addEventListener("click", e => {

    e.preventDefault();

    // 만약 클릭된 a태그에 "current" 클래스가 있을 경우
    // == 현재 페이지 숫자를 클릭한 경우
    if(item.classList.contains("current")){
      return;
    } 

    // const -> let 으로 변경
    let pathname = location.pathname; // 현재 게시판 조회 요청 주소

    // 클릭된 버튼이 <<, <, >, >> 인 경우
    // console.log(item.innerText);
    switch(item.innerText){
      case '<<' :   // 1 페이지로 이동
        pathname += "?cp=1";
        break;

      case '<'  : 
        pathname += "?cp=" + pagination.prevPage;
        break;

      case '>'  : 
        pathname += "?cp=" + pagination.nextPage;
        break;

      case '>>' : 
        pathname += "?cp=" + pagination.maxPage;
        break;

      default: 
        pathname += "?cp=" + item.innerText; 
        // location.href = pathname + "?cp=" + item.innerText; 
    }

    /* 검색인 경우 pathname 변수에 쿼리 스트링 추가 */

    // URLSearchParams : 쿼리스트링을 관리하는 객체
    // - 쿼리스트링 생성, 기존 쿼리 스트링을 k:v 형태로 분할 관리
    const params = new URLSearchParams(location.search);

    const key = params.get("key");
    const query = params.get("query");

    if (key !== null) { // 검색인 경우 쿼리 스트링 추가
      pathname += `&key=${key}&query=${query}`
    }

    // 페이지 이동
    location.href = pathname;

  });

});


const noticeInsertBtn = document.querySelector("#noticeInsertBtn");

// 등록 버튼
noticeInsertBtn.addEventListener("click", e => {
  location.href = "notice/insert";
});


// 삭제 수정 버튼 동작
const updateBtn = document.querySelectorAll(".updateBtn");
const deleteBtn = document.querySelectorAll(".deleteBtn");



/* 화면 로딩 후 삭제 수정 버튼 이벤트 부여 */
document.addEventListener("DOMContentLoaded", () => {

  for (let i = 0; i < updateBtn.length; i++) {

    // 삭제 버튼 이벤트
    deleteBtn[i].addEventListener("click", () => {

      const noticeNo = deleteBtn[i].closest(".notice-box").children[0];

      if (confirm("정말 삭제 하시겠습니까?") === false) return;
  
      const url = "/admin/notice/delete";    // 요청 주소
    
      const form = document.createElement("form");
      form.action = url;            // 요청 주소
      form.method = "POST";         // 메소드 지정
    
      const input = document.createElement("input");
      input.type  = "hidden";
      input.name  = "noticeNo";
      input.value = noticeNo.innerText;  
    
      form.append(input); 
    
      document.querySelector("body").append(form);
    
      form.submit(); 

    })

    // 수정 버튼 이벤트
    updateBtn[i].addEventListener("click", () => {

      const noticeNo = deleteBtn[i].closest(".notice-box").children[0];

      const url = "/admin/notice/update";    // 요청 주소
    
      const form = document.createElement("form");
      form.action = url;            // 요청 주소
      form.method = "POST";         // 메소드 지정
    
      const input = document.createElement("input");
      input.type  = "hidden";
      input.name  = "noticeNo";
      input.value = noticeNo.innerText;  
    
      form.append(input); 
    
      document.querySelector("body").append(form);
    
      form.submit(); 

    })

  }

  

});
