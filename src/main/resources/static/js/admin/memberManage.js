const memberList = document.querySelector("#memberList");

const selectMemberList = () => {

  fetch("/admin/selectMemberList")
  .then(response => {
    // 응답 성공 시 JSON 형태의 응답 데이터를 JS 객체로 변경
    if (response.ok) return response.json();
    throw new Error("조회 오류");
  })
  .then(list => {
    // console.log(list);

    // 2) 조회 결과인 list 를 반복 접근해서
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
      let str = (member.memberAuth === 1) ? '차단' : '활동';
      if (member.memberDelFl === 'Y') str = '탈퇴'
      td4.innerText = str;

      const td5 = document.createElement("td");
      const blockBtn = document.createElement("button");
      blockBtn.innerText = "차단";
      td5.append(blockBtn);

      const td6 = document.createElement("td");
      const secessionBtn = document.createElement("button");
      secessionBtn.innerText = "탈퇴";
      td6.append(secessionBtn);
    

      
      tr.append(td1, td2, td3, td4, td5, td6);

      memberList.append(tr);
      
    })

  })
  .catch(err => console.error(err));

};



/* 페이지 로딩(렌더링) 끝난 후 수행 */
document.addEventListener("DOMContentLoaded", () => {
  selectMemberList();
})