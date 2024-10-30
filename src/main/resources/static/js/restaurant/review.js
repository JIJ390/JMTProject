let rowNum = 1;
let sort = 1;
const restaurantNo = document.querySelector(".getRestaurantNo").value;
const addBtnDiv = document.querySelector(".addBtnDiv");
const reviewList = document.querySelector(".reviewList")
const button = document.getElementById('popupButton');
const popup = document.getElementById('popup');

const newList = document.querySelector(".new");
const oldList = document.querySelector(".old");
const reviewSize = 0;

// 팝업 토글
button.addEventListener('click', () => {
  popup.classList.toggle('hidden');
});


// 리뷰 신고 버튼 기능
getReportEvent = () => {
  const reviewReportBtn = document.querySelectorAll(".popupButtonReview");
  const popupReview = document.querySelectorAll(".popupReview");

  for (let i = 0; i < reviewReportBtn.length; i++) {
    reviewReportBtn[i]?.addEventListener("click", () => {
      const report1 = document.querySelectorAll(".report1");
      const report2 = document.querySelectorAll(".report2");
      const report3 = document.querySelectorAll(".report3");
      const report4 = document.querySelectorAll(".report4");
      if (popupReview[i]?.classList.contains("reviewPoppupHidden")) {
        for (let i = 0; i < popupReview.length; i++) {
          popupReview[i].classList.add("reviewPoppupHidden")
        }
        report1[i].classList.add("selectReportBtn")
        popupReview[i]?.classList.remove("reviewPoppupHidden");
      }
      else {
        const reportContent = document.querySelectorAll(".reportContent");

        for (let j = 0; j < report1.length; j++) {
          report1[j].classList.remove("selectReportBtn");
          report2[j].classList.remove("selectReportBtn");
          report3[j].classList.remove("selectReportBtn");
          report4[j].classList.remove("selectReportBtn");
          reportContent[j].value = "";
        }
        popupReview[i].classList.add("reviewPoppupHidden")
      }
    })
  }
}

// 리뷰 신고 버튼 선택 기능
getReportBtnEvent = () => {
  const report1 = document.querySelectorAll(".report1");
  const report2 = document.querySelectorAll(".report2");
  const report3 = document.querySelectorAll(".report3");
  const report4 = document.querySelectorAll(".report4");

  for (let i = 0; i < report1.length; i++) {

    report1[i]?.addEventListener("click", () => {
      if (report1[i].classList.contains("selectReportBtn")) {
        report1[i].classList.remove("selectReportBtn");
      }
      else {
        report1[i].classList.add("selectReportBtn");
        report2[i].classList.remove("selectReportBtn");
        report3[i].classList.remove("selectReportBtn");
        report4[i].classList.remove("selectReportBtn");
      }
    })
    report2[i]?.addEventListener("click", () => {
      if (report2[i].classList.contains("selectReportBtn")) {
        report2[i].classList.remove("selectReportBtn");
      }
      else {
        report1[i].classList.remove("selectReportBtn");
        report2[i].classList.add("selectReportBtn");
        report3[i].classList.remove("selectReportBtn");
        report4[i].classList.remove("selectReportBtn");
      }
    })
    report3[i]?.addEventListener("click", () => {
      if (report3[i].classList.contains("selectReportBtn")) {
        report3[i].classList.remove("selectReportBtn");
      }
      else {
        report1[i].classList.remove("selectReportBtn");
        report2[i].classList.remove("selectReportBtn");
        report3[i].classList.add("selectReportBtn");
        report4[i].classList.remove("selectReportBtn");
      }
    })
    report4[i]?.addEventListener("click", () => {
      if (report4[i].classList.contains("selectReportBtn")) {
        report4[i].classList.remove("selectReportBtn");
      }
      else {
        report1[i].classList.remove("selectReportBtn");
        report2[i].classList.remove("selectReportBtn");
        report3[i].classList.remove("selectReportBtn");
        report4[i].classList.add("selectReportBtn");
      }
    })

  }
}


// 리뷰 제출 버튼
getReportSubmitFn = () => {
  const reportSubmit = document.querySelectorAll(".reportSubmit");

  for (let i = 0; i < reportSubmit.length; i++) {

    reportSubmit[i]?.addEventListener("click", () => {

      const reportContent = document.querySelectorAll(".reportContent");
      const selectReportBtn = document.querySelector(".selectReportBtn");
      const popupReview = document.querySelectorAll(".popupReview");
      const reviewNo = document.querySelector(".getReviewNo").value;

      fetch("/review/report?reportContent=" + reportContent[i]?.value + "&reportType=" + selectReportBtn.innerText + "&reviewNo=" + reviewNo)
        .then(Response => {
          if (Response.ok) {
            return Response.text();
          }
          throw new Error("신고 실패");
        })
        .then(result => {
          if (result > 0) {
            alert("신고 완료");

            
            const report1 = document.querySelectorAll(".report1");
            const report2 = document.querySelectorAll(".report2");
            const report3 = document.querySelectorAll(".report3");
            const report4 = document.querySelectorAll(".report4");
            const reportContent = document.querySelectorAll(".reportContent");
            
            for (let i = 0; i < report1.length; i++) {
              report1[i].classList.remove("selectReportBtn");
              report2[i].classList.remove("selectReportBtn");
              report3[i].classList.remove("selectReportBtn");
              report4[i].classList.remove("selectReportBtn");
              reportContent[i].value = "";
            }
            popupReview[i].classList.add("reviewPoppupHidden")
            
          }
          else if(result < 0){
            alert("이미 신고한 리뷰입니다")
          }
        })
        .catch(err => {
          alert(err);
        })

    })

  }



}
















// 수정 버튼 요소 읽어와 이벤트 추가
getUpdateEvent = () => {
  const updateBtn = document.querySelectorAll(".updateBtn");
  const reviewNoList = document.querySelectorAll(".getReviewNo");

  for (let i = 0; i < updateBtn.length; i++) {
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

  for (let i = 0; i < deleteBtn.length; i++) {
    deleteBtn[i].addEventListener("click", () => {

      if (confirm("정말 삭제하시겠습니까?")) {

        location.href = "/review/delete?restaurantNo=" + restaurantNo + "&reviewNo=" + reviewNoList[i].value;
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
      getReportEvent();
      getReportBtnEvent();
      getReportSubmitFn();

      if(Number(document.querySelectorAll(".maxSize")[0].value) === 1){
        document.querySelector(".addBtnDiv").style.display = "none";
      }

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

