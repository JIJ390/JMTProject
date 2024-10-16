const MainHeartBtn = document.querySelectorAll(".heartBtn");
const MainStoreBtn = document.querySelectorAll(".store-box");


//클릭 이벤트 전파되지 않게 설정 

for( let i = 0 ; i < MainStoreBtn.length ; i++){
  MainStoreBtn[i].addEventListener('click', (event) => {
    alert("div 클릭 됨");
  })
}

for( let i = 0 ; i < MainHeartBtn.length ; i++){
  MainHeartBtn[i].addEventListener('click', (event) => {
  event.stopPropagation(); // 이벤트 전파를 막음
  alert('버튼이 클릭되었습니다!')
})
};

// ----------------------------------------------------
// 메뉴 누를시 레이어 팝업

const menu = document.querySelector(".menu");
const MenuContainer = document.querySelector(".menu-container");

menu.addEventListener('click', () => {

  if(MenuContainer.style.display=="block"){
    MenuContainer.style.display="none"
  }else{
    MenuContainer.style.display="block"
  }

  

});