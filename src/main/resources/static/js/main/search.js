const heartBtn = document.querySelector(".heartBtn");
const storeBtn= document.querySelector(".store-section");
    

//클릭 이벤트 전파되지 않게 설정 

heartBtn.addEventListener('click', (event) => {
  event.stopPropagation(); // 이벤트 전파를 막음
  alert('버튼이 클릭되었습니다!');
});



storeBtn.addEventListener('click', (event) => {
    storeBtn.classList.toggle('active');
    event.stopPropagation(); // 이벤트 전파를 막음
    alert("div 클릭 됨");
  });  


// ----------------------- 
const menu = document.querySelector(".menu");
const MenuContainer = document.querySelector(".menu-container");

menu.addEventListener('click', () => {

  if(MenuContainer.style.display=="block"){
    MenuContainer.style.display="none"
  }else{
    MenuContainer.style.display="block"
  }

  
});