const restaurantRegistBtn = document.querySelector("#restaurantRegistBtn");
// 페이지 이동을 위한 버튼 모두 얻어오기
const pageNoList = document.querySelectorAll(".pagination a");

const restaurantDetail = document.querySelector("#restaurantDetail");

let restaurantNoTemp;


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
const selectRestaurant = (url) => {
  fetch(url)
  .then(response => {
    if (response.ok) return response.json();
  })
  .then(map => {

    restaurantDetail.classList.remove("popup-hidden");

    const restaurant = map.restaurant;
    const menuList = map.menuList;

    console.log(menuList);
    console.log(restaurant);

    restaurantNoTemp = restaurant.restaurantNo;

    // 필요한 요소 얻어오기
    const selectRestaurantName = document.querySelector("#selectRestaurantName");
    const selectLoctionName = document.querySelector("#selectLoctionName");
    const selectCategoryName = document.querySelector("#selectCategoryName");
    const selectRestaurantTel = document.querySelector("#selectRestaurantTel");
    const selectRestaurantAddress = document.querySelector("#selectRestaurantAddress");

    const restaurantImg1 = document.querySelector("#restaurantImg1");
    const restaurantImg2 = document.querySelector("#restaurantImg2");


    // 해당 요소에 정보 담기
    selectRestaurantName.innerText = restaurant.restaurantName;
    selectLoctionName.innerText = `# ${restaurant.locationName}`;
    selectCategoryName.innerText = `# ${restaurant.categoryName}`;
    selectRestaurantTel.innerText = restaurant.restaurantTel;
    selectRestaurantAddress.innerText = restaurant.restaurantAddress;

    restaurantImg1.src = restaurant.restaurantImg1;
    restaurantImg2.src = restaurant.restaurantImg2;

    const menuArea = document.querySelector("#menuArea");

    menuArea.innerHTML = "";

    menuList.forEach(menu => {


      const div1 = document.createElement("div");
      const div2 = document.createElement("div");
      const span1 = document.createElement("span");
      const span2 = document.createElement("span");
      const span3 = document.createElement("span");


      span1.innerText = menu.menuName;
      span2.innerText = "ㆍㆍㆍㆍㆍㆍ";
      span3.innerText = menu.menuPrice;

      div1.append(span1, span2, span3);
      div2.append(span1, div1);
      div2.classList.add("menu-div");

      menuArea.append(div2);
    })


  });
};




/** 
 * 팝업 닫는 버튼
*/
document.querySelector("#popupClose").addEventListener("click", () => {
  restaurantDetail.classList.add("popup-hidden");
})


// 등록 버튼 이동
restaurantRegistBtn.addEventListener("click", e => {
  location.href = "restaurant/regist";
});


document.addEventListener("DOMContentLoaded", () => {
  // DOMContentLoaded : 화면이 모두 로딩된 후
  document.querySelector("#searchQuery").value = "";

  
  // 수정, 삭제 버튼에 이벤트 추가
  deleteRestaurant(restaurantNoTemp);
  updateRestaurant(restaurantNoTemp);

  // id="restaurantList" 후손 중 a 태그 모두 선택 => 노드 리스트(a)
  document.querySelectorAll("#restaurantList a").forEach((a) => {
    // 매개변수 a : 반복마다 하나씩 요소가 꺼내져 저장되는 변수

    // a 기본 이벤트 막고 selectRestaurant() 호출하게 하기
    a.addEventListener("click", e => {

      e.preventDefault();
      // 여러 div 가 감싼 형태 다른 요소로 인식
      // console.log(e.target);
      // console.log(e.currentTarget);
      selectRestaurant(a.href);
    })
  })

});


/**
 * 팝업창 가게 삭제 버튼 클릭 동작 
 */
const deleteRestaurant = (restaurantNo) => {

  const deleteBtn = document.querySelector("#deleteBtn");

  deleteBtn.addEventListener("click", () => {

    if (confirm("정말 삭제 하시겠습니까?") == false) return;
  
    const url = "/admin/restaurant/delete";    // 요청 주소
  
    const form = document.createElement("form");
    form.action = url;            // 요청 주소
    form.method = "POST";         // 메소드 지정
  
    const input = document.createElement("input");
    input.type  = "hidden";
    input.name  = "restaurantNo";
    input.value = restaurantNo;  
  
    form.append(input); 
  
    document.querySelector("body").append(form);
  
    form.submit(); 
    
  });

};




/**
 * 팝업창 가게 수정 버튼 클릭 동작 
 */
const updateRestaurant = (restaurantNo) => {

  const updateBtn = document.querySelector("#updateBtn");

  updateBtn.addEventListener("click", () => {

    const url = "/admin/restaurant/updateView";    // 요청 주소
  
    const form = document.createElement("form");
    form.action = url;            // 요청 주소
    form.method = "POST";         // 메소드 지정
  
    const input = document.createElement("input");
    input.type  = "hidden";
    input.name  = "restaurantNo";
    input.value = restaurantNo;  
  
    form.append(input); 
  
    document.querySelector("body").append(form);
  
    form.submit(); 

  });

}

