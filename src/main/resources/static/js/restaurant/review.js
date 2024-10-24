let rowNum = 1;
let sort = 1;
const restaurantNo = document.querySelector(".getRestaurantNo").value;
const addBtnDiv = document.querySelector(".addBtnDiv");
const reviewList = document.querySelector(".reviewList")


selectReview = () => {
  fetch("/review/selectReview?rowNum=" + rowNum + "&restaurantNo=" + restaurantNo + "&sort=" + sort)
    .then(Response => {
      if (Response.ok) {
        return Response.text();
      }
      throw new Error("리뷰 조회 실패");
    })
    .then(html => {

      reviewList.innerHTML = html;

    })
    .catch(err => console.log(err));

}

const button = document.getElementById('popupButton');
const popup = document.getElementById('popup');

button.addEventListener('click', () => {
  popup.classList.toggle('hidden');
});

const newList = document.querySelector(".new");
const oldList = document.querySelector(".old");

newList.addEventListener("click", () => {
  sort = 1;
  popup.classList.toggle('hidden');
  selectReview();
})

oldList.addEventListener("click", () => {
  sort = 2;
  popup.classList.toggle('hidden');
  selectReview();
})

addBtnDiv.addEventListener("click", () => {
  rowNum++;
  selectReview();
})

document.addEventListener("DOMContentLoaded", () => {
  selectReview();
})

