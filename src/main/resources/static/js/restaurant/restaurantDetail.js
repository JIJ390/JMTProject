const reviewBtn = document.querySelector(".reviewBtn");
reviewBtn.addEventListener("click", () => {

  const form = document.createElement("form");
  form.action = "/review/add";
  form.method = "POST"

  submit();

});

const reviewBtn1 = document.querySelector(".reviewBtn1");
reviewBtn1.addEventListener("click", () => {
  location.href = "/restaurant/add"
});
