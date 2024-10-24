const memberList = document.querySelector("#memberList");
const paginationArea = document.querySelector("#paginationArea");
let lastCp = 1; // 마지막으로 클릭한 cp 저장하는 변수



/**
 * 회원 정보 불러오는 함수
 * 
 * 검색 조건, 페이지네이션 추가해야 함!!
 */
const selectMemberList = (cp) => {

  // memberList 내부 HTML 초기화
  memberList.innerHTML = "";
  paginationArea.innerHTML = "";

  // 검색 조건 모두 얻어오기
  const condition = {
    searchCondition : document.querySelector("[name=searchCondition]:checked").value,
    searchKey : document.querySelector("#searchKey").value,
    searchQuery : document.querySelector("#searchQuery").value,
    cp : cp
  }

  console.log(condition);

  fetch("/admin/member/selectMemberList", {
    method : "POST", 
    headers: {"Content-Type": "application/json"}, 
    body : JSON.stringify(condition)

  })
  .then(response => {
    // 응답 성공 시 JSON 형태의 응답 데이터를 JS 객체로 변경
    if (response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(result => {
    console.log(result);

    const list = result.memberList;
    const pagination = result.pagination;

    // 검색 결과 없을 시
    if (list.length === 0) {
      const tr = document.createElement("tr");
      
      const td = document.createElement("td");
      td.innerText = "검색 결과가 없습니다";

      td.setAttribute('colSpan','5');

      tr.append(td);
      memberList.append(tr);
      return;
    }


    // 조회 결과인 list 를 반복 접근해서
    //    #memberList 에 조회된 내용으로 tr, td, th 만들어 넣기

    //  for of
    //  for in
    //  forEach
    list.forEach(member => {
      // 매개변수 member == 조회된 list 에서 하나씩 꺼낸 요소
      
      // tr 요소 만들기
      const tr = document.createElement("tr");
      
      // th 요소 만들어서 회원 정보 세팅
      const td1 = document.createElement("td");
      td1.innerText = member.memberNo;

      const td2 = document.createElement("td");
      td2.innerText = member.memberName;

      const td3 = document.createElement("td");
      td3.innerText = member.memberEmail;
      
      const td4 = document.createElement("td");
      let str = (member.memberAuth == 1) ? '차단' : '활동';
      if (member.memberDelFl === 'Y') str = '탈퇴'
      td4.innerText = str;


      // 차단 버튼
      const td5 = document.createElement("td");
      const blockBtn = document.createElement("button");
      let str2;

      switch (member.memberAuth) {
        case '0' : str2 = '차단'; break;
        case '1' : str2 = '복구'; break;
        default : str2 = '불가';
      }

      blockBtn.innerText = str2;
      td5.append(blockBtn);

      if (member.memberDelFl === 'Y') {
        // 탈퇴 상태일때 변경 불가
        blockBtn.disabled = true;
      } 
      else if (member.memberAuth === '2') {
        // 관리자 계정 차단 불가
        blockBtn.disabled = true;
      }
      else {
        // 차단 버튼 이벤트 추가
        blockBtn.addEventListener("click", () => {

          fetch("/admin/member/block", {
            method : "PUT", 
            headers: {"Content-Type": "application/json"}, 
            body : member.memberNo
          })
          .then(response => {
            if(response.ok) return response.text();
            throw new Error("차단 여부 변경 실패 : " + response.status);
          })
          .then(result => {
            if (result > 0) alert("차단 여부 변경되었습니다");

            selectMemberList(lastCp);
            selectMemberStatus();
          })
          .catch(err => console.error(err));
        })
      }



      // 탈퇴 버튼
      const td6 = document.createElement("td");
      const secessionBtn = document.createElement("button");

      // 회원 상태에 따라 다른 문구 출력
      let str3 = (member.memberDelFl === 'Y' ? '복구' : '탈퇴');
      if (member.memberAuth === '2') {
        str3 = '불가';
      }


      secessionBtn.innerText = str3;
      td6.append(secessionBtn);

      if (member.memberAuth === '2') {
        // 관리자 계정 탈퇴 불가
        secessionBtn.disabled = true;
      }
      else {
        // 차단 버튼 이벤트 추가
        secessionBtn.addEventListener("click", () => {

          fetch("/admin/member/secession", {
            method : "PUT", 
            headers: {"Content-Type": "application/json"}, 
            body : member.memberNo
          })
          .then(response => {
            if(response.ok) return response.text();
            throw new Error("탈퇴 여부 변경 실패 : " + response.status);
          })
          .then(result => {
            if (result > 0) alert("탈퇴 상태 변경되었습니다");

            selectMemberList(lastCp);
            selectMemberStatus();
          })
          .catch(err => console.error(err));
        })
      }


      const td7 = document.createElement("td");
      const loginBtn = document.createElement("button");
      loginBtn.innerText = "로그인";
      td7.append(loginBtn);

      // 만약 탈퇴 상태인 경우 로그인 버튼 비활성화
      if (member.memberDelFl === 'Y') {
        loginBtn.disabled = true;
        
      } else {

        // 탈퇴 상태가 아닌 경우
        // 만들어진 로그인 버튼에 클릭 이벤트 추가
        loginBtn.addEventListener("click", () => {

          // body 태그 제일 마지막에 form 태그를 추가해
          // 저출하는 형식으로 코드 작성
          // WHY? POST 방식 요청을 하고 싶기 때문에
          // form / Ajax 만 POST 방식

          const form = document.createElement("form");
          form.action = "/admin/member/directLogin"; // 요청 주소
          form.method = "POST";         // 메소드 지정

          const input = document.createElement("input");
          input.type  = "hidden";
          input.name  = "memberNo";
          input.value = member.memberNo;  // 반복문 내부

          form.append(input); // form 자식으로 input 추가

          // body 태그 자식으로 form 추가
          document.querySelector("body").append(form);

          form.submit(); // 제출
        })
      }

    

      
      tr.append(td1, td2, td3, td4, td5, td6, td7);

      memberList.append(tr);

    })

    // 페이지네이션 영역 생성
    paging(pagination);

  })
  .catch(err => console.error(err));

};


/**
 * 현재 회원 현황 불러오는 함수
 */
const selectMemberStatus = () => {

  fetch("/admin/member/selectMemberStatus")
  .then(response => {
    if (response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(map => {
    console.log(map);

    const totalMember = document.querySelector("#totalMember");
    const activeMember = document.querySelector("#activeMember");
    const blockMember = document.querySelector("#blockMember");
    const secessionMember = document.querySelector("#secessionMember");

    totalMember.innerText = map.totalMember;
    activeMember.innerText = map.activeMember;
    blockMember.innerText = map.blockMember;
    secessionMember.innerText = map.secessionMember;
    
  })
  .catch(err => console.error(err));
};



/**
 * pagination 만드는 함수
 */
const paging = (pagination) => {

  const ul = document.createElement("ul");

  ul.classList.add("pagination");

  const li1 = document.createElement("li");
  const span1 = document.createElement("span");
  span1.innerHTML = "&lt;&lt;";
  li1.append(span1);

  const li2 = document.createElement("li");
  const span2 = document.createElement("span");
  span2.innerHTML = "&lt;";
  li2.append(span2);

  ul.append(li1, li2);



  for (let i = pagination.startPage; i <= pagination.endPage; i++) {

    const li = document.createElement("li");
    const span = document.createElement("span");

    span.innerText = i;

    // 현재 페이지 조회 중 일 경우 current 클래스 추가
    if (i === pagination.currentPage) {
      span.classList.add("current");
    }

    li.append(span);
    ul.append(li);
  }


  const li3 = document.createElement("li");
  const span3 = document.createElement("span");
  span3.innerHTML = "&gt;";

  li3.append(span3);

  const li4 = document.createElement("li");
  const span4 = document.createElement("span");
  span4.innerHTML = "&gt;&gt;";
  li4.append(span4);

  ul.append(li3, li4);

  paginationArea.append(ul);



  // 페이지 이동을 위한 버튼 모두 얻어오기
  const pageNoList = document.querySelectorAll(".pagination span");


  // 페이지 이동 버튼 이벤트 추가
  pageNoList?.forEach((item, index) => {

    // 클릭 되었을 때
    item.addEventListener("click", e => {

      // 클릭된 버튼이 <<, <, >, >> 인 경우
      // console.log(item.innerText);
      switch(item.innerText){
        case '<<' :   // 1 페이지로 이동
          lastCp = 1;
          break;
  
        case '<'  : 
          lastCp = pagination.prevPage;
          break;
  
        case '>'  : 
          lastCp = pagination.nextPage;
          break;
  
        case '>>' : 
          lastCp = pagination.maxPage;
          break;
  
        default: 
          lastCp = item.innerText; 
      }

      console.log(pagination.currentPage);
      console.log(item.innerText);

      selectMemberList(lastCp);


      item.classList.add("current");


    });
  
  });

}




////////////////////////////////////////////////////////////////////////////////////////


/* 페이지 로딩(렌더링) 끝난 후 수행 */
document.addEventListener("DOMContentLoaded", () => {
  selectMemberList(1);
  selectMemberStatus();

  // 검색 버튼 클릭 이벤트
  document.querySelector("#searchBtn").addEventListener("click", () => {
    selectMemberList(1);
    selectMemberStatus();
  });


  // 엔터 입력 시 검색
  document.querySelector("#searchQuery").addEventListener("keyup", e => {
    // 입력한 키가 Enter인 경우
    if(e.key == "Enter"){
      selectMemberList(1);
      selectMemberStatus();
    }
  });

});
