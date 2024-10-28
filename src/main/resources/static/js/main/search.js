const heartBtn = document.querySelectorAll(".heartBtn");
const storeSection = document.querySelectorAll(".store-section");
const restaurantNo = document.querySelectorAll(".restaurantNo");


//클릭 이벤트 전파되지 않게 설정 

for (let i = 0; i < storeSection.length; i++) {
  storeSection[i].addEventListener('click', (event) => {
    location.href = "/restaurant/view?restaurantNo="+restaurantNo[i].value
  })
};

for (let i = 0; i < heartBtn.length; i++) {
  heartBtn[i].addEventListener('click', (event) => {
    event.stopPropagation(); // 버튼에 이벤트 전파 방지 
    alert("하트 클릭")
  })
};




// ----------------------- 
const menu = document.querySelector(".menu");
const MenuContainer = document.querySelector(".menu-container");

menu.addEventListener('click', () => {

  if (MenuContainer.style.display == "block") {
    MenuContainer.style.display = "none"
  } else {
    MenuContainer.style.display = "block"
  }


});

//---------------------- 검색 값 남기기






//---------------------------------------------------------------------------