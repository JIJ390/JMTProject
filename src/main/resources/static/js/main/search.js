const heartBtn = document.querySelectorAll(".heartBtn");
const storeSection = document.querySelectorAll(".store-section");
const restaurantNo = document.querySelectorAll(".restaurantNo");


//클릭 이벤트 전파되지 않게 설정 

// for (let i = 0; i < storeSection.length; i++) {
//   storeSection[i].addEventListener('click', (event) => {
//     location.href = "/restaurant/view?restaurantNo="+restaurantNo[i].value
//   })
// };

// for (let i = 0; i < heartBtn.length; i++) {
//   heartBtn[i].addEventListener('click', (event) => {
//     event.stopPropagation(); // 버튼에 이벤트 전파 방지 
//     alert("하트 클릭")
//   })
// };


// ----------------------- 
for (let i = 0;   i < storeSection.length; i++) {
  storeSection[i].addEventListener('click', () => {
    location.href = "/restaurant/view?restaurantNo=" + restaurantNo[i].value

  })
}
for (let i = 0; i < heartBtn.length; i++) {
  heartBtn[i].addEventListener('click', (event) => {

    console.log("aaa");
    event.stopPropagation(); // 이벤트 전파를 막음
    if (loginCheck === false) { //로그인이 되어있지않을때에
      alert("로그인이 필요합니다.")
      return;
    }
    const restaurantNo1 = restaurantNo[i].value;
    fetch("/like", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: restaurantNo1
    }).then(response => {
      if(response.ok) return response.text(); 
    })
      .then(result => { 
        console.log("result : ", result);
        //  fa-solid 하트 채우기  / fa-regular 하트 비우기
        if (result == 1) { // 채우기
          event.target.classList.remove("fa-solid");
          event.target.classList.add("fa-regular");
        } else { // 비우기
          event.target.classList.remove("fa-regular");
          event.target.classList.add("fa-solid");
        }
        
      })
    })
  };
  




//---------------------------------------------------------


const categoryBtns = document.querySelectorAll(".categoryBtns");
const result = document.querySelector(".result");

const searchFrm = document.querySelector("#searchFrm");

for(let i = 0 ; i < categoryBtns.length ; i++){

  
  categoryBtns[i].addEventListener("click", () => {
    console.log("aaa");

    result.value = categoryBtns[i].innerText;

    console.log(result.value);

    searchFrm.submit();
  })
}



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

    const result = params.get("result");
    const searchCode = params.get("searchCode");
    const tag = params.get("tag");
    const region = params.get("region");



    if ((tag !== null) || (region !== null)) { // 검색인 경우 쿼리 스트링 추가
      pathname += `&result=${result}&searchCode=${searchCode}&tag=${tag}&region=${region}`
    }

    else if ((result !== null) || (searchCode !== null) ) { // 검색인 경우 쿼리 스트링 추가
      pathname += `&result=${result}&searchCode=${searchCode}`
    }

    // 페이지 이동
    location.href = pathname;

  });

});


