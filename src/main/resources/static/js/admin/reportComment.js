const reportCommentDetail = document.querySelector("#reportCommentDetail");
const blockBtn = document.querySelector("#blockBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const reportContentBtn = document.querySelector("#reportContentBtn");

let memberNoTemp;
let reportNoTemp;
let boardNoTemp;
let commentNoTemp;
let urlTemp;


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


// 검색 기능
(()=>{

  // 쿼리스트링 모두 얻어와 관리하는 객체
  const params = new URLSearchParams(location.search);
    
  const key = params.get("key");
  const query = params.get("query");
    
  if (key === null) return; // 검색이 아니면 함수 종료
    
  // 검색어 화면에 출력하기
  document.querySelector("#searchQuery").value = query;
    
    // 검색조건 선택하기                  id 가 searchKey의 자식 중 option 모두(4개) 선택
  const options = document.querySelectorAll("#searchKey > option");
    
  options.forEach( op => {
      // op : <option> 태그
    if (op.value === key) { // option 의 value 와 key 가 같다면
      op.selected = true; // selected 속성 추가
      return;
    }
  })
    
})();




// 팝업 레이어 주소 가져와서 비동기로 조회 => 팝업 내용 채우기
const selectReportComment = (url) => {
  urlTemp = url;

  fetch(url)
  .then(response => {
    if (response.ok) return response.json();
  })
  .then(map => {

    // 삭제 차단 버튼 활성화
    deleteBtn.disabled = false;
    blockBtn.disabled = false;

    reportCommentDetail.classList.remove("popup-hidden");

    const reportComment = map.reportComment;
    const member = map.member;


    memberNoTemp = member.memberNo;
    commentNoTemp = reportComment.commentNo;
    reportNoTemp = reportComment.reportCommentNo;
    boardNoTemp = reportComment.boardNo;
    

    console.log(boardNoTemp);

    // 신고 관련 정보 요소 얻어오기
    const reportTypeName = document.querySelector("#reportTypeName");
    const reportCommentNo = document.querySelector("#reportCommentNo");
    const reportMemberName = document.querySelector("#reportMemberName");
    const reportDate = document.querySelector("#reportDate");
    const reportContent = document.querySelector("#reportContent");

    reportTypeName.innerText = reportComment.reportTypeName;
    reportCommentNo.innerText = `# ${reportComment.reportCommentNo}`;
    reportMemberName.innerText = `${reportComment.memberName} | `;
    reportDate.innerText = reportComment.reportCommentDate;

    reportContent.innerText = reportComment.reportCommentContent;

    console.log(commentNoTemp);

    

    // 신고된 댓글+ 작성자 정보
    const memberNo = document.querySelector("#memberNo");
    const memberName = document.querySelector("#memberName");
    const memberStatus = document.querySelector("#memberStatus");
    const boardLink = document.querySelector("#boardLink");

    memberNo.innerHTML = member.memberNo;
    memberName.innerHTML = member.memberName;
    boardLink.href = "/admin/report/board/detail?boardNo=" +  boardNoTemp;

    let str = (member.memberAuth === 1) ? '차단' : '활동';
    if (member.memberDelFl === 'Y') str = '탈퇴'

    memberStatus.innerHTML = str;

    
    // 만약 탈퇴 상태 혹은 차단 상태일 경우 버튼 비활성화
    if ((member.memberDelFl === 'Y') || (member.memberAuth === 1) ) {
      blockBtn.disabled = true;
    } 

    const commentDelFl = reportComment.commentDelFl;
    console.log(commentDelFl);

    // 만약 이미 삭제된 댓글일 경우 버튼 비활성화
    if (commentDelFl === 'Y') {
      deleteBtn.disabled = true;
    }


    const reportFeedBox = document.querySelector(".report-feed-box");

    reportFeedBox.innerHTML = "";

    
    const reportFeedBtn = document.querySelector("#reportFeedBtn");

    if (reportComment.reportCommentFl === 'Y') {

      // 이미 처리 완료된 신고의 경우 유저 차단, 글 삭제 불가
      deleteBtn.disabled = true;
      blockBtn.disabled = true;

      div = document.createElement("div");
      div.innerText = reportComment.reportCommentFeed;
      div.classList.add("report-feed");

      reportFeedBox.append(div);

      reportFeedBtn.disabled = true;
      reportFeedBtn.innerText = '이미 조치 완료된 신고입니다';
      reportFeedBtn.classList.remove("blue");
      reportFeedBtn.classList.add("gray");

    }
    else {



      textarea = document.createElement("textarea");
      textarea.placeholder = "조치 사항을 적어주세요";
      textarea.name = 'reportCommentFeed';
      textarea.classList.add("report-feed");
  
      reportFeedBox.append(textarea);

      reportFeedBtn.disabled = false;
      reportFeedBtn.innerText = '조치 완료';
      reportFeedBtn.classList.remove("gray");
      reportFeedBtn.classList.add("blue");
    }
  });
};




/**
 * 해당 회원 차단
 * @param {*} memberNo 
 */
const memberBlock = (memberNo) => {
  fetch("/admin/member/block", {
    method : "PUT", 
    headers: {"Content-Type": "application/json"}, 
    body : memberNo
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("차단 실패 : " + response.status);
  })
  .then(result => {

    if (result > 0) alert("차단 되었습니다");

    selectReportComment(urlTemp);

  })
  .catch(err => console.error(err));
}

/**
 * 댓글 삭제
 * @param {} commentNo 
 */
const deleteComment = (commentNo) => {
  fetch("/admin/report/comment/delete", {
    method : "PUT", 
    headers: {"Content-Type": "application/json"}, 
    body : commentNo
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("삭제 실패 : " + response.status);
  })
  .then(result => {

    if (result > 0) alert("삭제 되었습니다");

    selectReportComment(urlTemp);

  })
  .catch(err => console.error(err));

}






/**
 * 처리 완료 버튼 누를 시 동작
 */
const reportFeed = () => {

  const reportFeedFrm = document.querySelector("#reportFeedFrm");
  const reportFeedBtn = document.querySelector("#reportFeedBtn");

  reportFeedBtn.addEventListener("click", () => {

    // textarea 요소 가져오기
    const textarea = document.querySelector(".report-feed");
    
    if (textarea.value.trim().length < 3) {
      alert("조치 사항은 3 글자 이상으로 입력해 주세요");
      return;
    }

    const input = document.createElement("input");
    input.type  = "hidden";
    input.name  = "reportCommentNo";
    input.value = reportNoTemp;  
  
    reportFeedFrm.append(input); 
  
    reportFeedFrm.submit(); 
  });

};


/** 
 * 현황판 최신화
 * 
*/
const selectReportStatus = () => {

  fetch("/admin/report/comment/status")
  .then(response => {
    if (response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(map => {
    console.log(map);

    const totalReportCount = document.querySelector("#totalReportCount");
    const checkedReportCount = document.querySelector("#checkedReportCount");
    const uncheckedReportCount = document.querySelector("#uncheckedReportCount");
    const todayReportCount = document.querySelector("#todayReportCount");

    totalReportCount.innerText = map.totalCount;
    checkedReportCount.innerText = map.checkedCount;
    uncheckedReportCount.innerText = map.uncheckedCount;
    todayReportCount.innerText = map.todayCount;
    
  })
  .catch(err => console.error(err));
  
};





/** 
 * 팝업 닫는 버튼
*/
document.querySelector("#popupClose").addEventListener("click", () => {
  reportCommentDetail.classList.add("popup-hidden");
})



/* 팝업 이벤트 추가 */
document.addEventListener("DOMContentLoaded", () => {
  // DOMContentLoaded : 화면이 모두 로딩된 후
  document.querySelector("#searchQuery").value = "";

  // 차단 버튼 이벤트 추가
  blockBtn.addEventListener("click", () => {
    memberBlock(memberNoTemp);
  });

  // 삭제 버튼 이벤트 추가
  deleteBtn.addEventListener("click", () => {
    deleteComment(commentNoTemp);
  })

  // 처리 완료 버튼 동작 추가
  reportFeed();
  selectReportStatus();

  // id="restaurantList" 후손 중 a 태그 모두 선택 => 노드 리스트(a)
  document.querySelectorAll("#reportCommentList a").forEach((a) => {
    // 매개변수 a : 반복마다 하나씩 요소가 꺼내져 저장되는 변수

    // a 기본 이벤트 막고 selectReportComment() 호출하게 하기
    a.addEventListener("click", e => {

      e.preventDefault();
      // 여러 div 가 감싼 형태 다른 요소로 인식
      // console.log(e.target);
      // console.log(e.currentTarget);
      selectReportComment(a.href);
    })
  })

});