let rowNum = 1;
let sort = 1;
const restaurantNo = document.querySelector(".getRestaurantNo").value;
const addBtnDiv = document.querySelector(".addBtnDiv");
const reviewList = document.querySelector(".reviewList")
const button = document.getElementById('popupButton');
const popup = document.getElementById('popup');

const newList = document.querySelector(".new");
const oldList = document.querySelector(".old");

// 팝업 토글
button.addEventListener('click', () => {
  popup.classList.toggle('hidden');
});


// 수정 버튼 요소 읽어와 이벤트 추가
getUpdateEvent = () => {
  const updateBtn = document.querySelectorAll(".updateBtn");
  const reviewNoList = document.querySelectorAll(".getReviewNo");

  for(let i = 0; i < updateBtn.length; i++){
    updateBtn[i].addEventListener("click", () => {

      const form = document.createElement("form");
      form.action = "/review/update";
      form.method = "POST"

      const reviewNo = reviewNoList[i].value;

      const input = document.createElement("input");
      input.type = "hidden";
      input.value = reviewNo;
      input.name = "reviewNo";

      const input2 = document.createElement("input");
      input2.type = "hidden";
      input2.value = restaurantNo;
      input2.name = "restaurantNo";

      form.append(input);
      form.append(input2);

      document.querySelector("body").append(form);
      form.submit();

    })
  }
}

// 삭제 버튼 요소 읽어와 이벤트 추가
getDeleteEvent = () => {
  const deleteBtn = document.querySelectorAll(".deleteBtn");
  const reviewNoList = document.querySelectorAll(".getReviewNo");

  for(let i = 0; i < deleteBtn.length; i++){
    deleteBtn[i].addEventListener("click", () => {
      
      if(confirm("정말 삭제하시겠습니까?")){

        location.href ="/review/delete?restaurantNo="+restaurantNo+"&reviewNo="+reviewNoList[i].value;
      }

    })
  }
}

// 비동기로 리뷰 리스트 조회
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

      getUpdateEvent();
      getDeleteEvent();

    })
    .catch(err => console.log(err));
}


// 최신순 정렬
newList.addEventListener("click", () => {
  sort = 1;
  popup.classList.toggle('hidden');
  selectReview();
})


// 오래된순 정렬
oldList.addEventListener("click", () => {
  sort = 2;
  popup.classList.toggle('hidden');
  selectReview();
})


// 더 보기
addBtnDiv.addEventListener("click", () => {
  rowNum++;
  selectReview();
})




// 화면 로딩시 
document.addEventListener("DOMContentLoaded", () => {
  selectReview();
})

