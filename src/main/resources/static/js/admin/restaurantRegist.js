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
if (document.querySelector("#findAddressBtn") !== null) {
  document.querySelector("#findAddressBtn")
    .addEventListener("click", findAddress);
};



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



/* 유효성 검사!!! */////////////////////////////////////////////////////

/* 필수 입력 항목의 유효성 검사 여부를 체크하기 위한 객체(체크리스트) */
const form = document.querySelector("#restaurantResistFrm");

form.addEventListener("submit", e => {
  const restaurantName = document.querySelector("[name=restaurantName]");
  const inputRadio = document.querySelector("[name=inputRadio]");
  const menuName = document.querySelectorAll("[name=menuName]");
  const menuPrice = document.querySelectorAll("[name=menuPrice]");
  const restaurantTel = document.querySelector("[name=restaurantTel]");

  if (restaurantName.value.trim().length === 0) {
    alert("가게명을 작성해 주세요");
    restaurantName.focus();
    e.preventDefault();
    return;
  }
});