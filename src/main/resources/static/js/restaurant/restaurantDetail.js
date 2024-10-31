const reviewBtn = document.querySelector(".reviewBtn");

reviewBtn.addEventListener("click", () => {

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

