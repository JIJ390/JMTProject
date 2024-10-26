const commentClick = document.querySelectorAll(".comment-toggle");
const c = document.querySelectorAll(".c");
const boardNoList = document.querySelectorAll(".boardNoList");
const commentArea = document.querySelectorAll(".commentArea");






for (let i = 0; i < commentClick.length; i++) {

  commentClick[i].addEventListener("click", () => {
// 처음 main 창에서 check가 없는상태
    if (!commentClick[i].classList.contains("check")) {

      commentClick[i].classList.add("check");

      c[i].innerText = '댓글 닫기';

      fetch("/board/boardComment?boardNo=" + boardNoList[i].value)
        .then(response => {
          if (response.ok) {
            return response.text();
          }
          throw new Error("댓글 조회 실패");
        })
        .then(html => {
          commentArea[i].innerHTML = html;
        })
        .catch(err => {console.log(err)});
    }


    else {

      commentClick[i].classList.remove("check");

      c[i].innerText = '댓글 보기';

      // 덮어쓰기@@
      commentArea[i].innerHTML = "";
    }
  })
}

//-------------------------------------------------------------------------------------------------

// 댓글 내용 요소
const commentContent = document.querySelector(".comment-context");

/** 댓글 등록함수(AJAX)
 * @param parentCommentNo : 부모댓글 번호 (없음 undefined)
 */
const intsertComment = (parentCommentNo) =>{

  // 서버에 제출할 값을 저장할 객체


}