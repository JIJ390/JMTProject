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
    document.getElementById("btnResult").textContent = "카테고리 : #" +  value; //저장할 요소 불러와 textContent 넣어주기
    console.log(value); //value값 넘어오는거보기
    
    
    if(value == null){ //선택한 음식 카테고리가 없다면
      alert("선택한 카테고리가 존재하지않습니다.");
    }else{
      foodCategorySubmit.addEventListener("click", () => {
        tag.value = value;

        foodCategoryPopUp.classList.add("foodCategoryPopUp-close");
      })
    }


  })
});











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










