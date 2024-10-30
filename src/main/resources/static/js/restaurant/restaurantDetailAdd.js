const goToList = document.querySelector(".goToList");
goToList.addEventListener("click", () => {
  const restaurantNo = document.querySelector(".getRestaurantNo").value;
  location.href = "/restaurant/view?restaurantNo="+restaurantNo;
})


const suggestBtn1 = document.querySelector(".suggestBtn1");
const suggestBtn2 = document.querySelector(".suggestBtn2");

suggestBtn1.addEventListener("click", () => {
  if(suggestBtn2.classList.contains("selectBtn")){
    suggestBtn2.classList.remove("selectBtn");
    suggestBtn1.classList.add("selectBtn")
    return;
  }
  suggestBtn1.classList.add("selectBtn");
})



suggestBtn2.addEventListener("click", () => {
  if(suggestBtn1.classList.contains("selectBtn")){
    suggestBtn1.classList.remove("selectBtn");
    suggestBtn2.classList.add("selectBtn");
    return;
  }
  suggestBtn2.classList.add("selectBtn");
})






//---------------------------------------------------
// 리뷰 업로드 유효성 검사


const uploadBtn = document.querySelector(".uploadBtn");
const formTag = document.querySelector(".pormTag")

formTag.addEventListener("submit", (e) => {

  const content = document.querySelector("#content").value.trim();
  const selectBtnText = document.querySelector(".selectBtn").innerText;
  if(content === ""){
    alert("내용을 입력해주세요");
    e.preventDefault();
    return;
  }

  const likeFl = document.createElement("input");

  likeFl.value = selectBtnText;
  likeFl.type = "hidden";
  likeFl.name = "likeFl";

  formTag.append(likeFl);


})











