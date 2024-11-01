const reviewBtn = document.querySelector(".reviewBtn");

reviewBtn.addEventListener("click", () => {

  history.back();
});

const reviewBtn1 = document.querySelector(".reviewBtn1");

reviewBtn1.addEventListener("click", () => {

  const memberNo = document.querySelector(".getLoginMemberNo")?.value;
  const restaurantNo = document.querySelector(".getRestaurantNo").value;

  if (memberNo == null) {
    alert("로그인 후 이용해주세요")
    return;
  }

  const memberAuth = document.querySelector(".memberAuth").value;
  if (memberAuth == '1') {
    alert("차단당한 회원은 불가능합니다")
    return;
  }



  const form = document.createElement("form");
  form.action = "/restaurant/add";
  form.method = "POST"


  const input1 = document.createElement("input");

  input1.name = "memberNo"
  input1.value = memberNo
  input1.type = "hidden"

  form.append(input1);

  const input2 = document.createElement("input");

  input2.name = "restaurantNo"
  input2.value = restaurantNo
  input2.type = "hidden"

  form.append(input2);

  document.querySelector("body").append(form);

  form.submit();
});
const reviewBtn2 = document.querySelector(".reviewBtn2");

reviewBtn2.addEventListener("click", () => {

  const memberNo = document.querySelector(".getLoginMemberNo")?.value;
  const restaurantNo = document.querySelector(".getRestaurantNo").value;

  if (memberNo == null) {
    alert("로그인 후 이용해주세요")
    return;
  }

  const memberAuth = document.querySelector(".memberAuth").value;
  if (memberAuth == '1') {
    alert("차단당한 회원은 불가능합니다")
    return;
  }



  const form = document.createElement("form");
  form.action = "/restaurant/add";
  form.method = "POST"


  const input1 = document.createElement("input");

  input1.name = "memberNo"
  input1.value = memberNo
  input1.type = "hidden"

  form.append(input1);

  const input2 = document.createElement("input");

  input2.name = "restaurantNo"
  input2.value = restaurantNo
  input2.type = "hidden"

  form.append(input2);

  document.querySelector("body").append(form);

  form.submit();
});



const htBtn = document.querySelector(".htBtn");
htBtn.addEventListener("click", () => {

  if (loginCheck === false) {
    alert("로그인 후 이용해 주세요");
    return;
  }

  const restaurantNo = document.querySelector(".getRestaurantNo").value;

  fetch("/restaurant/bookMark?restaurantNo="+restaurantNo)
  .then(Response => {
    if(Response.ok){
      return Response.text();
    }
    throw new Error("찜 실패");
  })
  .then(result => {
    if(result > 0){
      fetch("/restaurant/bookMarkDelete?restaurantNo="+restaurantNo)
      .then(response => {
        if(response.ok){
          htBtn.classList.remove("fa-solid");
          htBtn.classList.add("fa-regular");
        }
      })
    }
    else{
      fetch("/restaurant/bookMarkAdd?restaurantNo="+restaurantNo)
      .then(response => {
        if(response.ok){
          htBtn.classList.add("fa-solid");
          htBtn.classList.remove("fa-regular");
        }
      })

    }
  })
  

})
