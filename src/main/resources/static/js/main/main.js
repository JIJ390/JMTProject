const MainHeartBtn = document.querySelectorAll(".heartBtn");
const MainStoreBtn = document.querySelectorAll(".store-box");
const foodCategoryPopUp = document.querySelector(".foodCategoryPopUp"); // 푸드 팝업

//클릭 이벤트 전파되지 않게 설정 

for (let i = 0; i < MainStoreBtn.length; i++) {
  MainStoreBtn[i].addEventListener('click', (event) => {
    alert("div 클릭 됨");
  })
}

for (let i = 0; i < MainHeartBtn.length; i++) {
  MainHeartBtn[i].addEventListener('click', (event) => {
    event.stopPropagation(); // 이벤트 전파를 막음
    alert('버튼이 클릭되었습니다!')
  })
};


foodCategoryBtn.addEventListener("click", e => {
  
  e.preventDefault();
  
  
  if(foodCategoryPopUp.classList.contains("foodCategoryPopUp-close")){
    foodCategoryPopUp.classList.remove("foodCategoryPopUp-close")
  }
});



const foodCategoryBtnValue = document.getElementsByName("valus")
foodCategoryBtnValue.forEach(function(button){ // 모든 각각에버튼을 클릭시
  

  const foodCategorySubmit = document.querySelector(".foodCategorySubmit"); //카테고리 값 버튼
  const tag = document.querySelector(".tag"); //카테고리 전달 버튼

  button.addEventListener("click", function(){
    var value = button.value; // 클릭된 버튼의 value값 가져옴
    document.getElementById("foodBtnResult").textContent = "카테고리 : #" +  value; //저장할 요소 불러와 textContent 넣어주기
    console.log(value); //value값 넘어오는거보기
    


      foodCategorySubmit.addEventListener("click", () => {

        tag.value = value;

        
        foodCategoryPopUp.classList.add("foodCategoryPopUp-close"); // 적용시 메인페이지에 값 저장
      })


  })
});



/* 지역 설정 */
const regionListBtn = document.querySelectorAll(".regionListBtn")
for(let i = 0; i < regionListBtn.length ; i++){

  regionListBtn[i].addEventListener("click", () => { // 버튼들중 하나를 클릭했을경우
// 클릭 시 추가 

for(let a = 0; a < regionListBtn.length ; a++){
  regionListBtn[a].classList.remove("regionListBtn-bc")
}

regionListBtn[i].classList.add("regionListBtn-bc") // 선택된거 클래스 추가

const regionBtnResult = document.querySelector("#regionBtnResult")

const value = regionListBtn[i].innerText; 
console.log(value);

regionBtnResult.textContent = value;

const region = document.querySelector(".region")


region.value = value;

})
}

/* ========================================= 팝업 닫기 ====================================== */
const regionCategorySubmit = document.querySelector(".regionCategorySubmit")
const regionCategoryPopUp = document.querySelector(".regionCategoryPopUp")
const regoinBtn = document.querySelector("#regionCategoryBtn")

//값 전달 버튼
regionCategorySubmit.addEventListener("click", () => {
  regionCategoryPopUp.classList.add("regionCategoryPopUp-close")

})
// 카테고리 클릭시 팝업 열리는
regoinBtn.addEventListener("click", () => {
  regionCategoryPopUp.classList.remove("regionCategoryPopUp-close")
})

/* 음식종류 취소 버튼 */
const foodCloseBtn= document.querySelector(".foodClosePopup") // 취소버튼
foodCloseBtn.addEventListener("click", () => {
  foodCategoryPopUp.classList.add("foodCategoryPopUp-close");
});

/* 지역 선택 취소 버튼 */
const regionCloseBtn = document.querySelector(".regionClosePopUp") // 취소버튼
regionCloseBtn.addEventListener("click", () => {
  regionCategoryPopUp.classList.add("regionCategoryPopUp-close");
})


// ----------------------------------------------------
// 메뉴 누를시 레이어 팝업

const menu = document.querySelector(".menu");
const MenuContainer = document.querySelector(".menu-container");

menu.addEventListener('click', () => {

  if (MenuContainer.style.display == "block") {
    MenuContainer.style.display = "none"
  } else {
    MenuContainer.style.display = "block"
  }



});








// ----------------------------------------------------------------------










