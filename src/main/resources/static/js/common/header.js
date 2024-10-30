// 헤더 마이 페이지 팝업 버튼

const popUpSpan = document.querySelector("#popUpSpan");
const MenuContainer = document.querySelector(".menu-container");

popUpSpan?.addEventListener("click", () => {
  
  if (MenuContainer.style.display == "block") {
    MenuContainer.style.display = "none"
  } else {
    MenuContainer.style.display = "block"
  }


})


document.addEventListener("DOMContentLoaded", () => {
  
  // 상단으로 올라가기
  const topBtn = document.querySelector("#topBtn");
  
  topBtn.addEventListener("click", () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth' // 부드러운 스크롤 효과
    });
  });

})