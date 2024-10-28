const reviewBtn = document.querySelector(".reviewBtn");

reviewBtn.addEventListener("click", () => {

  const memberNo = document.querySelector(".getLoginMemberNo").value;

  if (memberNo == null) {
    alert("로그인 후 이용해주세요")
    return;
  }


  const form = document.createElement("form");
  form.action = "/restaurant/add";
  form.method = "POST"


  const input = document.createElement("input");

  input.name = "memberNo"
  input.value = memberNo
  input.type = "hidden"

  form.append(input);

  document.querySelector("body").append(form);

  form.submit();

});

const reviewBtn1 = document.querySelector(".reviewBtn1");

reviewBtn1.addEventListener("click", () => {


  const memberNo = document.querySelector(".getLoginMemberNo").value;

  if (memberNo == null) {
    alert("로그인 후 이용해주세요")
    return;
  }


  const form = document.createElement("form");
  form.action = "/restaurant/add";
  form.method = "POST"


  const input = document.createElement("input");

  input.name = "memberNo"
  input.value = memberNo
  input.type = "hidden"

  form.append(input);

  document.querySelector("body").append(form);

  form.submit();
});


const popupReview = document.getElementById('popupReview');
const popupButtonReview = document.getElementById('popupButtonReview');

popupButtonReview?.addEventListener('click', () => {
  popupReview.classList.toggle('hidden');
});