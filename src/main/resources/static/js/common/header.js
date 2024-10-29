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