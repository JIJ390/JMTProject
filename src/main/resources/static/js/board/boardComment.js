const commentClick = document.querySelectorAll(".comment-toggle");
const c = document.querySelectorAll(".c");
const boardNoList = document.querySelectorAll(".boardNoList");
const commentArea = document.querySelectorAll(".commentArea");

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

for (let i = 0; i < commentClick.length; i++) {

  commentClick[i].addEventListener("click", () => {
    // 처음 main 창에서 check가 없는상태
    const post = commentClick[i].closest(".post");
    const commentArea = post.querySelector(".commentArea");

    if (!commentClick[i].classList.contains("check")) {

      commentClick[i].classList.add("check");

      c[i].innerText = '댓글 닫기';

      selectCurrBoardCommentList(post)
    }


    else {

      commentClick[i].classList.remove("check");

      c[i].innerText = '댓글 보기';

      // 덮어쓰기@@
      commentArea.innerHTML = "";
    }
  })
}

//-------------------------------------------------------------------------------------------------

// 댓글 목록 조회
const selectCurrBoardCommentList = (post) => {
  const boardNo = post.dataset.boardNo;

  const commentArea = post.querySelector(".commentArea");

  fetch("/board/boardComment?boardNo=" + boardNo)
    .then(response => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("댓글 조회 실패");
    })
    .then(html => {
      commentArea.innerHTML = html;

      /** [주의사항] */
      // innerHTML로 새로 만들어진 요소에는
      // 이벤트 리스너가 추가 되어있지 않기 때문에
      // 답글, 수정, 삭제등이 동작하지 않는다!!

      addEventCommentWrite(post);
      addEventChildComment(); // 답글버튼에 클릭이벤트 추가
      addEventDeleteComment(); // 삭제버튼에 이벤트 추가
      addEventUpdateComment(); // 수정버튼에 이벤트 추가


    })
    .catch(err => { console.log(err) });
}


//-------------------------------------------------------------------------------------------------

// 댓글 입력
const addEventCommentWrite = (post) => {

  const boardNo = post.dataset.boardNo;
  const commentContentArea = post.querySelector(".comment-write")
  const commentWriteBtn = post.querySelector(".commentBtn-write")
  const commentPictureBtn = post.querySelector(".commentBtn-picture")


  commentWriteBtn.addEventListener("click", () => {
    const content = commentContentArea.value;

    // key : value
    const data = {};
    data.boardNo = boardNo; // 댓글이 작성된 게시글 번호
    data.commentContent = content; // 작성된 댓글 내용

    fetch("/board/boardComment", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    })
      .then(response => {
        if (response.ok) {
          alert("댓글 등록 성공")
          selectCurrBoardCommentList(post);
          return;
        }
        throw new Error("댓글 등록 실패");
      })
      .catch(err => console.error(err));

  })

}

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

/** 댓글 삭제 */

/**
 * @param btn = 삭제버튼
 */
const deleteComment = (btn) => {

  if (confirm("정말 삭제하시겠습니까?") === false) {

    return;
  }

  // 삭제할 댓글번호 얻어오기
  const commentDiv = btn.closest("div"); // 댓글 또는 답글
  const commentNo = commentDiv.dataset.commentNo; // 댓글번호

  fetch("/board/boardComment", {
    method: "DELETE",
    headers: { "Content-Type": "application/json" },
    body: commentNo
  })
    .then(response => {
      if (response.ok) return response.text();
      throw new Error("댓글 삭제 실패");
    })
    .then(result => {
      if (result > 0) {
        alert("삭제되었습니다.");
        const post = commentDiv.closest(".post");
        selectCurrBoardCommentList(post);
      } else {
        alert("삭제 실패");
      }

    })
    .catch(err => console.error(err));


}

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

/** 댓글 수정 */
let beforeCommentRow;

showUpdateComment = (btn) => {
  

  // const commentList1 = document.querySelectorAll(".comment-post-header");
  // for (let i = 0; i < commentList1.length; i++) {
  //   if (commentList1[i].children[1].children[2].classList.contains(".textTarget")) {
  //     selectCurrBoardCommentList(post);
  //   }
  // }
  if (document.querySelector(".textTarget")) {
    if (confirm("열린 수정창 닫으시겠습니까?")) {
      const temp = document.querySelector(".textTarget").closest(".comment-post-header");
      temp.after(beforeCommentRow);
      temp.remove();

      const uBtn = beforeCommentRow.children[2].children[0];
      const dBtn = beforeCommentRow.children[2].children[1];

      uBtn.addEventListener("click", () => { showUpdateComment(uBtn) });
      dBtn.addEventListener("click", () => { deleteComment(dBtn) });
    }
    else{
      return;
    }
  }

  const commentRow = btn.closest(".comment-post-header");
  beforeCommentRow = commentRow.cloneNode(true);

  const commentContent = btn.closest(".comment-post-header").children[1].children[2].innerText;


  btn.closest(".comment-post-header").children[1].children[2].remove();

  const textarea = document.createElement("textarea");

  textarea.value = commentContent;
  textarea.style.resize = "none";
  textarea.style.marginLeft = "30px";
  textarea.style.border = "1px solid #6EC3C2";
  textarea.style.width = "80%";
  textarea.classList.add("textTarget")

  btn.closest(".comment-post-header").children[1].append(textarea);
  /** 버튼생성 */
  //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

  const button1 = document.createElement("button");
  const button2 = document.createElement("button");

  button1.innerText = "수정하기";
  button2.innerText = "수정취소";

  button1.addEventListener("click", () => {

    /** commentNo 가 commentDiv 에 담겨있기때문 */
    const updateBtn = button1.closest(".commentDiv").dataset.commentNo;

    const textContent = document.querySelector(".textTarget").value;

    const data = {};
    data.commentNo = updateBtn;
    data.commentContent  = textContent;

    fetch("/board/boardComment",{
      method  : "PUT",
      headers : { "Content-Type": "application/json" },
      body    : JSON.stringify(data)
    })
    .then(response => {
      if(response.ok) return response.text();
      throw new Error("댓글 수정 실패");
    })
    .then(commentNo => {

      if(commentNo == 0){
        alert("댓글 수정 실패")
        return;
      }
      const post = button1.closest(".post");
      selectCurrBoardCommentList(post);

    })
    .catch(err => console.error(err));


  })



  /** 추가를 먼저해놔야 삭제먼저하면 추가도안된다 */
  btn.closest(".comment-post-header").children[2].append(button1);
  btn.closest(".comment-post-header").children[2].append(button2);

  /** 수정취소 눌렀을때 원상 복귀시키기 */
  button2.addEventListener("click", () => {

    commentRow.after(beforeCommentRow);
    commentRow.remove();

    const uBtn =  beforeCommentRow.children[2].children[0];
    const dBtn =  beforeCommentRow.children[2].children[1];
      
    uBtn.addEventListener("click", () => {showUpdateComment(uBtn)});
    dBtn.addEventListener("click", () => {deleteComment(dBtn)});
    // selectCurrBoardCommentList(post);
    // console.log(button2);

  


  })


  /** 원리 나중에 ★★ 먼저 [0]일때 삭제되면 끝이다.*/
  btn.closest(".comment-post-header").children[2].children[1].remove();
  btn.closest(".comment-post-header").children[2].children[0].remove();

}












// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ










// 댓글 내용 요소
const commentContent = document.querySelector(".comment-context");

/** 댓글 등록함수(AJAX)
 * @param parentCommentNo : 부모댓글 번호 (없음 undefined)
 */
const insertComment = (parentCommentNo) => {

  // 서버에 제출할 값을 저장할 객체
  const data = {};
  data.boardNo = boardNo; // 댓글이 작성된 게시글 번호
  data.commentContent = commentContent.value; // 작성된 댓글 내용

  // 매개변수로 전달받은 부모댓글 번호가 있다면
  // == 답글
  if (parentCommentNo !== undefined) {
    data.parentCommentNo = parentCommentNo;

    // 답글에 작성된 내용 얻어오기
    data.commnetContent =
      document.querySelector(".child-comment-content").value;
  }

  // Ajax
  fetch("/boardComment", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data) // JS 객체 -> JSON (문자열)
  })
    .then(response => {
      if (response.ok) return response.text();
      throw new Error("댓글등록 실패");
    })
    .then(commentNo => {

      if (commentNo == 0) { // 등록실패
        alert("댓글등록 실패");
        return;
      }

      alert("댓글이 등록되었습니다.");
      commentContent.value = ""; // textarea에 작성된 댓글내용 삭제
      selectCommentList(); // 댓글목록 비동기 조회 후 출력


    })
    .catch(err => console.error(err));


}






// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
/** 이벤트 추가구문을 모아두는 영역 */

// /** 작성 등록버튼 클릭동작 추가 */

// const addComment = document.querySelector("#addComment");
// addComment.addEventListener("click", () => {

//   // 1) 로그인 여부검사(boardDetail.html의 loginCheck 전역변수)
//   if (loginCheck == false) {
//     alert("로그인 후 이용해주세요");
//     return;
//   }

//   // 2) 댓글작성 여부검사
//   if (commentContent.value.trim().length === 0) {
//     alert("내용작성 후 등록버튼을 클릭해주세요")
//     return;
//   }

//   // 3) 1,2 통과시 댓글등록 함수 호출
//   insertComment();

// })

/** 화면에 존재하는 답글버튼을 모두 찾아 이벤트리스너 추가 */
const addEventChildComment = () => {

  const btns = document.querySelectorAll(".comment-child");
  btns.forEach(btn => {
    btn.addEventListener("click", () => {
      showChildComment(btn); // 답글 작성화면 출력함수 호출
    });
  })
}

/** 화면에 존재하는 댓글 삭제버튼에 이벤트리스너 추가 */
const addEventDeleteComment = () => {

  const btns = document.querySelectorAll(".comment-delete");
  btns.forEach(btn => {
    btn.addEventListener("click", () => {
      deleteComment(btn);
      // 클릭 시 deleteComment() 함수 호출
    });
  })
}


/** 화면에 존재하는 댓글 수정버튼에 이벤트리스너 추가 */
const addEventUpdateComment = () => {

  const btns = document.querySelectorAll(".comment-update");
  btns.forEach(btn => {
    btn.addEventListener("click", () => {
      showUpdateComment(btn);
      // 수정버튼 클릭 시 showUpdateComment() 호출
    });
  })
}

/** 화면코드 해석 완료 후 */
document.addEventListener("DOMContentLoaded", () => {

  addEventChildComment();  // 답글 버튼에 이벤트 추가
  addEventDeleteComment(); // 삭제 버튼에 이벤트 추가
  addEventUpdateComment(); // 수정 버튼에 이벤트 추가

});