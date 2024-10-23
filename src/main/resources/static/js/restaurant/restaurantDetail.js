const reviewBtn = document.querySelector(".reviewBtn");
reviewBtn.addEventListener("click", () => {
  location.href = "/restaurant/add"
})

const mainReviewContent = document.querySelector(".mainReviewContent");

const content = /*[[${review.reviewContent}]]*/ "리뷰 본문"

mainReviewContent.innerHTML = content;