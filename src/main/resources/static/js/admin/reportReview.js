const reportReviewDetail = document.querySelector("#reportReviewDetail");
const blockBtn = document.querySelector("#blockBtn");
const reportContentBtn = document.querySelector("#reportContentBtn");

let memberNoTemp;
let reportNoTemp;
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
const selectReportReview = (url) => {
  urlTemp = url;

  fetch(url)
  .then(response => {
    if (response.ok) return response.json();
  })
  .then(map => {



    reportReviewDetail.classList.remove("popup-hidden");

    const reportReview = map.reportReview;
    const member = map.member;


    // 신고 관련 정보 요소 얻어오기
    const reportTypeName = document.querySelector("#reportTypeName");
    const reportReviewNo = document.querySelector("#reportReviewNo");
    const reportMemberName = document.querySelector("#reportMemberName");
    const reportDate = document.querySelector("#reportDate");
    const reportContent = document.querySelector("#reportContent");

    reportNoTemp = reportReview.reportReviewNo;

    reportTypeName.innerText = reportReview.reportTypeName;
    reportReviewNo.innerText = `# ${reportReview.reportReviewNo}`;
    reportMemberName.innerText = `${reportReview.memberName} | `;
    reportDate.innerText = reportReview.reportReviewDate;

    reportContent.innerText = reportReview.reportReviewContent;


    // 신고된 게시글+ 작성자 정보
    const memberNo = document.querySelector("#memberNo");
    const memberName = document.querySelector("#memberName");
    const memberStatus = document.querySelector("#memberStatus");

    memberNo.innerHTML = member.memberNo;
    memberNoTemp = member.memberNo;

    memberName.innerHTML = member.memberName;

    let str = (member.memberAuth === 1) ? '차단' : '활동';
    if (member.memberDelFl === 'Y') str = '탈퇴'

    memberStatus.innerHTML = str;

    
    // 만약 탈퇴 상태 혹은 차단 상태일 경우 버튼 비활성화
    if ((member.memberDelFl === 'Y') || (member.memberAuth === 1) ) {
      blockBtn.disabled = true;
    } 


    const reportFeedBox = document.querySelector(".report-feed-box");

    reportFeedBox.innerHTML = "";

    
    const reportFeedBtn = document.querySelector("#reportFeedBtn");

    if (reportReview.reportReviewFl === 'Y') {

      console.log(reportReview.reportReviewFeed);

      div = document.createElement("div");
      div.innerText = reportReview.reportReviewFeed;
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
      textarea.name = 'reportReviewFeed';
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
    console.log(result);

    if (result > 0) alert("차단 되었습니다");

    selectReportReview(urlTemp);

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

    const input = document.createElement("input");
    input.type  = "hidden";
    input.name  = "reportReviewNo";
    input.value = reportNoTemp;  
  
    reportFeedFrm.append(input); 
  
    reportFeedFrm.submit(); 
  });

};






/** 
 * 팝업 닫는 버튼
*/
document.querySelector("#popupClose").addEventListener("click", () => {
  reportReviewDetail.classList.add("popup-hidden");
})



/* 화면 로딩 후 a 태그에 팝업 비동기 조회 이벤트 부여 */
document.addEventListener("DOMContentLoaded", () => {
  // DOMContentLoaded : 화면이 모두 로딩된 후
  document.querySelector("#searchQuery").value = "";

  // 차단 버튼 이벤트 추가
  blockBtn.addEventListener("click", e => {
    // 차단을 위한 회원 번호 가져오기
    memberBlock(memberNoTemp);
  });

  // 처리 완료 버튼 동작 추가
  reportFeed();

  // id="restaurantList" 후손 중 a 태그 모두 선택 => 노드 리스트(a)
  document.querySelectorAll("#reportReviewList a").forEach((a) => {
    // 매개변수 a : 반복마다 하나씩 요소가 꺼내져 저장되는 변수

    // a 기본 이벤트 막고 selectReportReview() 호출하게 하기
    a.addEventListener("click", e => {

      e.preventDefault();
      // 여러 div 가 감싼 형태 다른 요소로 인식
      // console.log(e.target);
      // console.log(e.currentTarget);
      selectReportReview(a.href);
    })
  })

});