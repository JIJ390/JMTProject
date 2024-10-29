const heartBtn = document.querySelectorAll(".heartBtn");
const storeSection = document.querySelectorAll(".store-section");
const restaurantNo = document.querySelectorAll(".restaurantNo");


//클릭 이벤트 전파되지 않게 설정 

// for (let i = 0; i < storeSection.length; i++) {
//   storeSection[i].addEventListener('click', (event) => {
//     location.href = "/restaurant/view?restaurantNo="+restaurantNo[i].value
//   })
// };

// for (let i = 0; i < heartBtn.length; i++) {
//   heartBtn[i].addEventListener('click', (event) => {
//     event.stopPropagation(); // 버튼에 이벤트 전파 방지 
//     alert("하트 클릭")
//   })
// };


// ----------------------- 
for (let i = 0; i < storeSection.length; i++) {
  storeSection[i].addEventListener('click', () => {
    location.href = "/restaurant/view?restaurantNo=" + restaurantNo[i].value

  })
}
for (let i = 0; i < heartBtn.length; i++) {
  heartBtn[i].addEventListener('click', (event) => {
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