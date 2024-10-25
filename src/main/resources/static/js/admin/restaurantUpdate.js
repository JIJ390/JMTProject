const findAddressBtn = document.querySelector("#findAddressBtn")

/* 다음 주소 API 로 주소 검색하기 */

function findAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
      // var extraAddr = ''; // 참고항목 변수, 필요 없음

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('postcode').value = data.zonecode;
      document.getElementById("address").value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("detailAddress").focus();
    }
  }).open();
}

/* 주소 검색 버튼 클릭 시 */
/* 버튼 있을 때만!!! 활성화 */
findAddressBtn.addEventListener("click", findAddress);

////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////
/* 메뉴 입력 테이블 추가 버튼 */

const menuPlusBtn = document.querySelector("#menuPlusBtn");
const menuMinusBtn = document.querySelector("#menuMinusBtn");

const tbBody = document.querySelector(".menuTable-body");

menuPlusBtn.addEventListener ("click", () => {

  const input1 = document.createElement("input");

  input1.setAttribute("type","text") ;
  input1.setAttribute("name","menuName") ;
  input1.setAttribute("placeholder","음식 이름을 입력해 주세요") ;

  const td1 = document.createElement("td");

  td1.append(input1);

  const input2 = document.createElement("input");

  input2.setAttribute("type","text") ;
  input2.setAttribute("name","menuPrice") ;
  input2.setAttribute("placeholder","음식 가격을 입력해 주세요") ;

  const td2 = document.createElement("td");

  td2.append(input2);

  const tr = document.createElement("tr");

  tr.append(td1);
  tr.append(td2);

  tbBody.append(tr);

});

menuMinusBtn.addEventListener("click", () => {

  const trList =  document.querySelectorAll('.menuTable-body > tr');

  if (trList.length < 3) {
    alert("최소 2 개 이상의 메뉴를 입력해 주세요.");
    return;
  } 

  // 마지막 tr 요소 선택
  const tr = document.querySelector('.menuTable-body > tr:last-child');
  tr.remove();
})


/* 미리 보기 이미지 구현 */

// 미리보기가 출력될 img 
const previewList = document.getElementsByClassName("preview");

// 가게 이미지를 선택할 input
const inputImageList = document.getElementsByClassName("inputImage");

// 마지막으로 선택된 파일을 저장할 배열
const lastValidFiles = [null, null];

const updatePreview = (file, order) => {

  // 선택된 파일이 지정된 크기를 초과한 경우 선택 막기
  const maxSize = 1024 * 1024 * 10;

  if (file.size > maxSize) {  // 파일 크기 초과 시
    alert("10 MB 이하의 이미지만 선택해 주세요");
    

    if (lastValidFiles[order] === null) {
      inputImageList[order].value = ""; // 선택 파일 삭제
      return;
    }

    const dataTransfer = new DataTransfer();

    dataTransfer.items.add(lastValidFiles[order]);

    inputImageList[order].files = dataTransfer.files;

    updatePreview(lastValidFiles[i], i); 


    return;
  }

  lastValidFiles[order] = file;

  const reader = new FileReader();

  reader.readAsDataURL(file);

  reader.addEventListener("load", e => {
    previewList[order].src = e.target.result;
  })

}

/* input 태그 이벤트 리스너 추가 */

for (let i = 0; i < inputImageList.length; i++) {

  inputImageList[i].addEventListener("change", e => {
    const file = e.target.files[0];

    if (file === undefined) { 

      if (lastValidFiles[i] === null) return;

      const dataTransfer = new DataTransfer();

      dataTransfer.items.add(lastValidFiles[i]);

      inputImageList[i].files = dataTransfer.files;

      updatePreview(lastValidFiles[i], i); 

      return;
    }

    updatePreview(file, i);
  })

} // for end







/* 필수 입력 항목의 입력 여부를 체크 */
const form = document.querySelector("#restaurantResistFrm");

form.addEventListener("submit", e => {

  /* 이미지 인풋 요소 미리보기 영역에서 선언*/
  

  const restaurantName = document.querySelector("[name=restaurantName]");
  const categoryRadio = document.querySelectorAll("[name=categoryNo]");
  const locationRadio = document.querySelectorAll("[name=locationNo]");
  const menuName = document.querySelectorAll("[name=menuName]");
  const menuPrice = document.querySelectorAll("[name=menuPrice]");
  const restaurantTel = document.querySelector("[name=restaurantTel]");

  /* 주소 */
  const postcode = document.querySelector("#postcode");
  const address = document.querySelector("#address");
  const detailAddress = document.querySelector("#detailAddress");

  // 음식 가격 정규 표현식  세 자리 이상의 숫자
  const priceFormat = /\d{3}/;

  // // 파일 이미지 형식 정규 표현식
  // const fileForm = /(.*?)\.(jpg|jpeg|png|gif|bmp|pdf)$/;


  // for (let i = 0; i < inputImageList.length; i++) {
  //   if(inputImageList[i].value.trim().length === 0) {
  //     alert("대표 이미지를 선택해 주세요");
  //     e.preventDefault();
  //     return;
  //   }

  //   if(!inputImageList[i].match(fileForm)) {
  //     alert("이미지 파일만 업로드할 수 있습니다");
  //     e.preventDefault();
  //     return;
  //   }
  // }



  if (restaurantName.value.trim().length === 0) {
    alert("가게명을 작성해 주세요");
    restaurantName.focus();
    e.preventDefault();
    return;
  }


  // 체크되었는지 확인하기 위한 flag 변수
  let flag = true;

  for (let i=0; i < categoryRadio.length; i++) {
    // input 이 체크된 경우가 있을 때 false
    if (categoryRadio[i].checked) flag = false;
  }

  if (flag) {
    alert("카테고리를 선택해 주세요");
    e.preventDefault();
    return;
  }


  flag = true;

  for (let i=0; i < locationRadio.length; i++) {
    if (locationRadio[i].checked) flag = false;
  }

  if (flag) {
    alert("지역을 선택해 주세요");
    e.preventDefault();
    return;
  }


  for (let i=0; i < menuName.length; i++) {
    if (menuName[i].value.trim().length < 2) {
      alert("최소 2 글자 이상의 음식 이름을 입력해주세요");
      menuName[i].focus();
      e.preventDefault();
      return;
    }
  }

  for (let i=0; i < menuPrice.length; i++) {
    if (!priceFormat.test(menuPrice[i].value.trim())) {
      menuPrice[i].focus();
      alert("가격은 세 자리 이상의 숫자로 입력해주세요");
      e.preventDefault();
      return;
    }
  }


  if ((restaurantTel.value.trim().lecgth === 0)) {
    alert("전화번호를 검색해 주세요");
    restaurantTel.focus();
    e.preventDefault();
    return;
  }

  // 주소창 둘 중 하나가 비었을 때
  if ((postcode.value.trim().length === 0) || (address.value.trim().length === 0)) {
    alert("주소를 검색해 주세요");
    findAddressBtn.focus();
    e.preventDefault();
    return;
  }

  if ((detailAddress.value.trim().length === 0)) {
    alert("상세 주소를 입력해 주세요");
    detailAddress.focus();
    e.preventDefault();
    return;
  }

});