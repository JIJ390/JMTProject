const restaurantRegistBtn = document.querySelector("#restaurantRegistBtn");
// 페이지 이동을 위한 버튼 모두 얻어오기
const pageNoList = document.querySelectorAll(".pagination a");


// 페이지 이동 버튼이 클릭 되었을 때
pageNoList?.forEach( (item, index) => {

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






// 등록 버튼 이동
restaurantRegistBtn.addEventListener("click", e => {
  location.href = "restaurant/regist";
});