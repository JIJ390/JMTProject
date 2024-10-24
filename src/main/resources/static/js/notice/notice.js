const pageNoList = document.querySelectorAll(".pagination a");



pageNoList?.forEach((item, index) => {
  item.addEventListener("click", e => {
    e.preventDefault();

    // 현재 페이지 숫자 클릭 시
    if(item.classList.contains("current")){
      return;
    }

    const pathname = location.pathname;

    // 클릭 버튼 << , < , > , >> 일 때 
    switch(item.innerText){
      case '<<' : location.href = pathname + "?cp=1"; break;
      case '<'  : location.href = pathname + "?cp=" + pagination.prevPage; break;
      case '>'  : location.href = pathname + "?cp=" + pagination.nextPage; break;
      case '>>' : location.href = pathname + "?cp=" + pagination.maxPage; break;
      default : location.href = pathname + "?cp=" + item.innerText; // 클릭 페이지 이동
    }

  })
});

document.addEventListener("DOMContentLoaded", () => {
  const btn = document.querySelector(".btn");

  const div = document.createElement("div");
  div.innerText = "new";
  
  btn.append(div);
});