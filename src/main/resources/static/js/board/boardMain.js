// 스크롤 가장위로 올라가는 효과
const topBtn = document.querySelector("#topBtn");

topBtn.onclick = function () {
  window.scrollTo({
    top: 0,
    behavior: 'smooth' // 부드러운 스크롤 효과
  });
};

// 글쓰기 버튼을 눌렀을때 글쓰기 페이지로 넘어가는 효과
const btn = document.querySelector(".writeBtn");

btn.addEventListener("click", () => {
  location.href = "/board/boardWrite";
})

// -------------------------------------------------

/* 삭제버튼 클릭 시
  - 삭제버튼 클릭시 "정말 삭제하시겠습니까?" confirm()

  -/boradMain/delete, POST 방식, 동기식 요청
  -> from 태그 생성 + 게시글 번호가 세팅된 input
  -> body 태그 제일 아래 추가해서 submit()

- 서버(Java)에서 로그인한 회원의 회원번호를 얻어와
  로그인한 회원이 작성한 글이 맞는지 SQL에서 검사
*/
// const boardNo = document.querySelector(".board-btn-area").dataset.boardNo;

// 삭제버튼 요소 얻어오기
const deleteBtn = document.querySelectorAll(".deleteBtn");

for (let i = 0; i < deleteBtn.length; i++) {

  // 삭제버튼이 존재할 때 이벤트 리스너 추가

  deleteBtn[i]?.addEventListener("click", () => {
    
    
    if (confirm("정말 삭제하시겠습니까?") == false) {
      return;
    }
    const boardNo = deleteBtn[i].closest(".board-btn-area").dataset.boardNo;



    const url = "/board/delete"; // 요청주소
    // 게시글 번호 == 전역변수 boardNo

    // form태그 생성
    const form = document.createElement("form");
    form.action = url;
    form.method = "POST";
    // POST는 누구나 할 수 없을때
    // form태그와 ajax 2가지 방식밖에 없음

    // input 태그생성
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = "boardNo";
    input.value = boardNo;

    form.append(input); // form 자식으로 input 추가

    // body 자식으로 form 추가
    document.querySelector("body").append(form);

    form.submit(); // 제출
  })
}

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

/* 수정버튼 클릭시
  - /board/updateView, POST, 동기식
  -> form 태그 생성
  -> body 태그 제일 아래 추가해서 submit()

  - 서버(Java)에서 로그인한 회원번호를 얻어와
    로그인한 회원이 작성한 글이 맞을경우
    해당글을 상세조회한 후
    수정화면으로 전환
*/

const updateBtn = document.querySelectorAll(".updateBtn");

for(let i=0; i<updateBtn.length; i++){

updateBtn[i]?.addEventListener("click", () => {
  // if(confirm("수정할거야?") == false){
  //   return;
  // }
  
  // location.href = "/board/updateView"; * 겟방식 &*&*
  const boardNo = deleteBtn[i].closest(".board-btn-area").dataset.boardNo;

  const form = document.createElement("form");

  form.action = "/board/updateView";
  form.method = "POST";

  const input = document.createElement("input");
  input.type = "hidden";
  input.name = "boardNo";
  input.value = boardNo;

  form.append(input);

  document.querySelector("body").append(form);

  form.submit();

})

};