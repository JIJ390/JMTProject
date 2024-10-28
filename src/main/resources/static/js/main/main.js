const mainHeartBtn = document.querySelectorAll(".heartBtn");
const mainStoreBtn = document.querySelectorAll(".store-box");
const foodCategoryPopUp = document.querySelector(".foodCategoryPopUp"); // 푸드 팝업

//클릭 이벤트 전파되지 않게 설정 


foodCategoryBtn.addEventListener("click", e => {

  e.preventDefault();


  if (foodCategoryPopUp.classList.contains("foodCategoryPopUp-close")) {
    foodCategoryPopUp.classList.remove("foodCategoryPopUp-close")
  }
});


/* 음식 종류에서 버튼 값 클릭시 저장하는 */
const foodCategoryBtnValue = document.getElementsByName("valus")
foodCategoryBtnValue.forEach(function (button) { // 모든 각각에버튼을 클릭시


  const foodCategorySubmit = document.querySelector(".foodCategorySubmit"); //카테고리 값 버튼
  const tag = document.querySelector(".tag"); //카테고리 전달 버튼

  button.addEventListener("click", function () {
    var value = button.value; // 클릭된 버튼의 value값 가져옴
    document.getElementById("foodBtnResult").textContent = "카테고리 : #" + value; //저장할 요소 불러와 textContent 넣어주기
    // console.log(value); //value값 넘어오는거보기



    foodCategorySubmit.addEventListener("click", () => {

      tag.value = value;


      foodCategoryPopUp.classList.add("foodCategoryPopUp-close"); // 적용시 메인페이지에 값 저장
    })


  })
});



/* 지역 설정 */
const regionListBtn = document.querySelectorAll(".regionListBtn")
for (let i = 0; i < regionListBtn.length; i++) {

  regionListBtn[i].addEventListener("click", () => { // 버튼들중 하나를 클릭했을경우
    // 클릭 시 추가 

    for (let a = 0; a < regionListBtn.length; a++) {
      regionListBtn[a].classList.remove("regionListBtn-bc")
    }

    regionListBtn[i].classList.add("regionListBtn-bc") // 선택된거 클래스 추가

    const regionBtnResult = document.querySelector("#regionBtnResult")

    const value = regionListBtn[i].innerText;
    console.log(value);

    regionCategorySubmit.addEventListener("click", () => {
      regionBtnResult.textContent = value;

      region.value = value;
    })

    const region = document.querySelector(".region")



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
const foodCloseBtn = document.querySelector(".foodClosePopup") // 취소버튼
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

menu?.addEventListener('click', () => {

  if (MenuContainer.style.display == "block") {
    MenuContainer.style.display = "none"
  } else {
    MenuContainer.style.display = "block"
  }



});








// ---------------------------------------------------------------------- 하트
/* 레스토랑 넘버 */
const restaurantNo = document.querySelectorAll(".restaurantNo");
const likeBtn = document.querySelectorAll("[likeBtn]"); //이름으로된 찜 버튼

for (let i = 0; i < mainStoreBtn.length; i++) {
  mainStoreBtn[i].addEventListener('click', () => {
    location.href = "/restaurant/view?restaurantNo=" + restaurantNo[i].value
  })
}

for (let i = 0; i < mainHeartBtn.length; i++) {
  mainHeartBtn[i].addEventListener('click', (event) => {
    event.stopPropagation(); // 이벤트 전파를 막음

    if (loginCheck === false) { //로그인이 되어있지않을때에
      alert("로그인이 필요합니다.")
      return;
    }

    const restaurantNo1 = restaurantNo[i].value;


    fetch("/like", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: restaurantNo1
    }).then(response => {
      if(response.ok) return response.text(); 
    })
      .then(result => {
        console.log("result : ", result);


        //  fa-solid 하트 채우기  / fa-regular 하트 비우기
        if (result == 1) { // 채우기
          event.target.classList.remove("fa-solid");
          event.target.classList.add("fa-regular");

        } else { // 비우기
          event.target.classList.remove("fa-regular");
          event.target.classList.add("fa-solid");
        }

        
      })
  })
};

