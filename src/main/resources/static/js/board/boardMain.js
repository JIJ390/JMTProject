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

btn?.addEventListener("click", e => {

  if (loginMember === null) {
    alert("로그인해야 글쓰기를 할 수 있습니다.");
    
    e.preventDefault();
    return;
  }

  if (loginMember.memberAuth == '1') {
    alert("차단당한 회원 입니다. 글쓰기를 이용하실 수 없습니다.");
    return;
  }

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

for (let i = 0; i < updateBtn.length; i++) {

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

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ무한무스크롤

const boardBox = document.querySelector(".board-box");


let currentPage = 2;
let isFetching = false;
let hasMore = true;

const plusBoardList = (currentPage, searchKey, searchQuery) => {
  // 로딩이 시작되면 로딩중 변수를 true 로 바꿔서 더는 로딩이 안되도록 
  isFetching = true;


  let url = "/board/boardPlus?cp=" + currentPage;

  // 검색일 때만 url 뒤에 파라미터들 추가
  if ((searchKey !== null) && (searchQuery !== null)) {
    url += `&key=${searchKey}&query=${searchQuery}`;
  }

  fetch(url)
    .then(Response => {
      if (Response.ok) {
        return Response.text();
      }
      throw new Error("게시글 조회 실패");
    })
    .then(html => {


      // 로딩이 끝나면 로딩중 변수를 false 로 바꿔서 다시 로딩이 되도록
      isFetching = false;

      // 데이터가 없으면 false;
      // 전체 html 길이가 0 일 때 더 이상 로딩이 안되도록
      if (html.trim().length === 0) {
        hasMore = false;
        return;
      }

      // div를 만들어서 비동기로 가져온 boardList 목록 담기 
      const div = document.createElement("div");
      div.classList.add("post-container");


      div.innerHTML = html;

      boardBox.append(div);


      // 강사님코드



      // div.querySelectorAll(".updateReport").forEach(item => {
      //   item.addEventListener("click", () => {
      //     if (item.classList.contains(".boardReportHidden")) {
      //       item.classList.remove(".boardReportHidden")
      //     }
      //     else {
      //       item.classList.add(".boardReportHidden")
      //     }

      //   })

      // })
      div.querySelectorAll(".comment-toggle").forEach(item => {
        item.addEventListener("click", () => {

          const post = item.closest(".post");
          const commentArea = post.querySelector(".commentArea");

          const ptag = item.children[1];

          if (!ptag.classList.contains("check")) {

            console.log(ptag);

            ptag.classList.add("check");

            ptag.innerText = '댓글 닫기';

            selectCurrBoardCommentList(post)
          }


          else {

            ptag.classList.remove("check");

            ptag.innerText = '댓글 보기';

            // 덮어쓰기@@
            commentArea.innerHTML = "";
          }

        })

      })

      // div.querySelectorAll(".updateReport").forEach(item => {
      //   item.addEventListener("click", () => {

      //     const updateReport = item.closest(".updateReport");
      //     const boardReport = item.closest(".boardReport");


      //     updateReport.addEventListener("click", () => {

      //       if (boardReport.classList.contains("boardReportHidden")) {
      //         boardReport.classList.remove("boardReportHidden")

      //       } else {

      //         boardReport.classList.add("boardReportHidden")
      //       }

      //     })
      //   })
      // })




      getReportEvent(); // 리뷰신고버튼
      reportBtn();
      getReportBtn();



    })

    .catch(err => console.log(err));

}



/**
 * 스크롤 가장 밑으로 내릴 때마다 이벤트 발생
 */
window.addEventListener('scroll', () => {

  if (isFetching || !hasMore) {
    return;
  }

  const documentHeight = document.documentElement.scrollHeight;


  // console.log("내린 스크롤 길이");
  // console.log(window.innerHeight + window.scrollY);
  // console.log("전체 화면 길이");
  // console.log(documentHeight);

  if ((window.innerHeight + window.scrollY) >= documentHeight - 100) {

    const searchParams = new URLSearchParams(location.search);
    const searchKey = searchParams.get("key");
    const searchQuery = searchParams.get("query");

    plusBoardList(currentPage, searchKey, searchQuery);


    currentPage++;
  }
})






//////////////////////////////////////////////
//////////////////////////////////////////////
//////////////////////////////////////////////
//////////////////////////////////////////////
//////////////////////////////////////////////
//boardComment.js


const commentClick = document.querySelectorAll(".comment-toggle");
const c = document.querySelectorAll(".c");
const boardNoList = document.querySelectorAll(".boardNoList");
const commentArea = document.querySelectorAll(".commentArea");

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

const commentOpen = () => {
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
};



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
      addEventDeleteComment(); // 삭제버튼에 이벤트 추가
      addEventUpdateComment(); // 수정버튼에 이벤트 추가

      commentReport();
      commentReportCheck();


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

    const getLoginMember = document.querySelector(".getLoginMember");

    if(getLoginMember == null){
      alert("댓글작성은 회원만 가능해요^^");
      commentContentArea.value="";
      return;
    }

    if(commentWriteBtn.value == '1'){
      alert("차단된 회원입니다. 댓글을 작성하실 수 없습니다");
      commentContentArea.value="";
      return;
    }

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
    else {
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

  button1.classList.add("button1")
  button2.classList.add("button1")

  button1.addEventListener("click", () => {

    /** commentNo 가 commentDiv 에 담겨있기때문 */
    const updateBtn = button1.closest(".commentDiv").dataset.commentNo;

    const textContent = document.querySelector(".textTarget").value;

    const data = {};
    data.commentNo = updateBtn;
    data.commentContent = textContent;

    fetch("/board/boardComment", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    })
      .then(response => {
        if (response.ok) return response.text();
        throw new Error("댓글 수정 실패");
      })
      .then(commentNo => {

        if (commentNo == 0) {
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

    const uBtn = beforeCommentRow.children[2].children[0];
    const dBtn = beforeCommentRow.children[2].children[1];

    uBtn.addEventListener("click", () => { showUpdateComment(uBtn) });
    dBtn.addEventListener("click", () => { deleteComment(dBtn) });
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

/** 무조건 추가하기 ★9★ㅁ8★★★ㅁ7ㅁ78ㅁ798ㅁ789ㅁ798 */
/** 화면코드 해석 완료 후 */
document.addEventListener("DOMContentLoaded", () => {

  commentOpen();

  addEventDeleteComment(); // 삭제 버튼에 이벤트 추가
  addEventUpdateComment(); // 수정 버튼에 이벤트 추가
  getReportEvent(); // 리뷰신고버튼
  reportBtn();
  getReportBtn();
  commentReportCheck();
  commentReportInsert();

});






//●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
//●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
//●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
//●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
//●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●
//●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●

// 게시글 report

// 리뷰 신고 버튼 기능

let index = 0;

let openBoardReport;

getReportEvent = () => {

  const updateReport = document.querySelectorAll(".updateReport");
  const boardReport = document.querySelectorAll(".boardReport");

  for (let i = index; i < updateReport.length; i++) {

    // updateReport[i].removeEventListener("click", getEventListeners( document.querySelector('#updateReport')).click[0].listener)

    updateReport[i].addEventListener("click", () => {
      const boardReport1 = document.querySelectorAll(".boardReport");
      if (boardReport[i]?.classList.contains("boardReportHidden")) {

        for (let j = 0; j < boardReport1.length; j++) {
          boardReport1[j].classList.add("boardReportHidden");
          boardReport1[j].children[0].classList.remove("click")
          boardReport1[j].children[1].classList.remove("click")
          boardReport1[j].children[2].classList.remove("click")
          boardReport1[j].children[3].value = "";

        }

        boardReport[i].classList.remove("boardReportHidden")

        openBoardReport = boardReport[i];

      } else {

        boardReport[i].classList.add("boardReportHidden")
      }


    })
    index = i + 1;
  }
}

let index2 = 0;
// 게시물신고 1,2,3 클릭된 애만 색나오게//
reportBtn = () => {
  const r1 = document.querySelectorAll(".r1");
  const r2 = document.querySelectorAll(".r2");
  const r3 = document.querySelectorAll(".r3");

  for (let i = index2; i < r1.length; i++) {

    r1[i].addEventListener("click", () => {
      r2[i].classList.remove("click");
      r3[i].classList.remove("click");
      r1[i].classList.add("click");
    })
    r2[i].addEventListener("click", () => {
      r1[i].classList.remove("click");
      r3[i].classList.remove("click");
      r2[i].classList.add("click");
    })
    r3[i].addEventListener("click", () => {
      r1[i].classList.remove("click");
      r2[i].classList.remove("click");
      r3[i].classList.add("click");
    })
    index2 = i + 1;
  }
}

let index3 = 0;

getReportBtn = () => {
  // 1,2,3
  // 텍스트에어리어
  // 제출버튼
  const getreportBtn = document.querySelectorAll(".reportBtn");

  for (let i = index3; i < getreportBtn.length; i++) {


    getreportBtn[i].addEventListener("click", e => {
      const click = document.querySelector(".click");

      const reportContent = click.closest(".boardReport").children[3].value;

      const boardNo = e.target.closest(".post").dataset.boardNo;

      fetch("/board/report?reportContent=" + reportContent + "&reportType=" + click.innerText + "&boardNo=" + boardNo)
        .then(response => {
          if (response.ok) return response.text();
          throw new Error("신고 제출 실패!");
        })
        .then(result => {
          if (result > 0) {
            alert("신고 완료");
            const boardReport1 = document.querySelectorAll(".boardReport");

            for (let j = 0; j < boardReport1.length; j++) {
              boardReport1[j].classList.add("boardReportHidden");
              boardReport1[j].children[0].classList.remove("click")
              boardReport1[j].children[1].classList.remove("click")
              boardReport1[j].children[2].classList.remove("click")
              boardReport1[j].children[3].value = "";

            }
          }
        })
        .catch(err => console.log(err));
    })
    index3 = i + 1;
  }

}


let index4 = 0;
commentReport = () => {
  const commentReport = document.querySelectorAll(".commentReport");
  const commentReportPopup = document.querySelector(".commentReportPopup");

  for (let i = index4; i < commentReport.length; i++) {
    commentReport[i].addEventListener("click", () => {

      const commentNo = commentReport[i].closest(".commentDiv").dataset.commentNo;
      document.querySelector(".getCommentNo").value = commentNo;
      console.log(commentNo);
      console.log(document.querySelector(".getCommentNo").value);
      commentReportPopup.classList.remove("commentPopupHidden");

    })
    index = i + 1;
  }

  const commentCloseBtn = document.querySelector(".commentCloseBtn");
  commentCloseBtn.addEventListener("click", () => {
    commentReportPopup.classList.add("commentPopupHidden");
  })

}

commentReportCheck = () => {
  const rc = document.querySelectorAll(".rc");
  for (let i = 0; i < rc.length; i++) {
    rc[i].addEventListener("click", () => {
      rc[0].classList.remove("commentclick");
      rc[1].classList.remove("commentclick");
      rc[2].classList.remove("commentclick");
      rc[i].classList.add("commentclick");
    })
  }
}

commentReportInsert = () => {
  const commentReportBtn = document.querySelector(".commentReportBtn")
  commentReportBtn.addEventListener("click", () => {

    const content = document.querySelector(".commentReportContent").value;
    const reportType = document.querySelector(".commentclick").innerText;
    const commentNo = document.querySelector(".getCommentNo").value;
    const rc = document.querySelectorAll(".rc");

    console.log(reportType);
    fetch("/board/commentReport?reportType=" + reportType + "&content=" + content + "&commentNo=" + commentNo)
      .then(response => {
        if (response.ok) return response.text();
        throw new Error("댓글 신고 실패");
      })
      .then(result => {
        if (result > 0) {
          alert("신고완료!");
  
              rc[0].classList.remove("commentclick");
              rc[1].classList.remove("commentclick");
              rc[2].classList.remove("commentclick");
     

          commentNo.value="0";
          document.querySelector(".commentReportContent").value="";
          document.querySelector(".commentReportPopup").classList.add("commentPopupHidden");
        }
      })
      .catch(err => console.log(err));
  })
}




// ------------------------------------------------------------------------------------------

document.addEventListener("DOMContentLoaded", () => {

  // 화면 클릭 시
  document.addEventListener("click", e => {
    // console.log(e.target); // 화면에서 클릭된 요소

    // 클릭된 요소가 신고 버튼인 경우
    if(e.target.classList.contains("updateReport")) return;
    
    // 클릭된 요소가 현재 열려있는 신고 팝업레이어인 경우 
    if(e.target == openBoardReport) return;
    
    // 현재 열려있는 신고 팝업레이어의 후손 요소 모두 얻어오기
    const elements = openBoardReport?.querySelectorAll("*");

    let flag = true;
    elements?.forEach(item => { // 클릭된 요소가 열려있는 신고 팝업의 후손인 경우 
      if(item == e.target){
        flag = false;
        return;
      } 
    });

    // 신고 버튼, 신고 팝업, 신고 팝업 후손이 아니면
    // 열려있는 신고 팝업 닫기
    if(flag)  openBoardReport.classList.add("boardReportHidden");

  });


})


