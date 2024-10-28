const checkObj = {
  "memberEmail"     : false,
  "memberPw"        : false,
  "memberPwConfirm" : false,
  "memberName"      : false,
  "authKey"         : false
};

/* 이메일 유효성 검사 */
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

// 이메일 메시지
const emailMessageObj = {};
emailMessageObj.normal = "메일을 받을 수 있는 이메일을 입력해주세요";
emailMessageObj.invaild = "알맞은 이메일 형식으로 작성해 주세요";
emailMessageObj.check = "인증번호 받기 버튼을 눌러주세요";

// 이메일이 입력될 때 마다 유효성 검사 수행
memberEmail.addEventListener("input", e => {

  // 입력된 값 얻어오기
  const inputEmail = memberEmail.value.trim();

  // 입력된 이메일이 없을 경우
  if(inputEmail.length === 0){
    
    // 이메일 메시지를 normal 상태 메시지로 변경
    emailMessage.innerText = emailMessageObj.normal;

    // #emailMessage에 색상 관련 클래스를 모두 제거
    emailMessage.classList.remove("confirm", "error");

    // checkObj에서 memberEmail을 false로 변경
    checkObj.memberEmail = false;

    memberEmail.value = ""; // 잘못 입력된 값(띄어쓰기) 제거
    
    return;
  }


  // 이메일 형식이 맞는지 검사(정규 표현식을 이용한 검사)

  // 이메일 형식 정규 표현식 객체
  const regEx = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // 입력 값이 이메일 형식이 아닌 경우
  if( regEx.test(inputEmail) === false ){ 
    emailMessage.innerText = emailMessageObj.invaild; // 유효 X 메시지
    emailMessage.classList.add("error"); // 빨간 글씨 추가
    emailMessage.classList.remove("confirm"); // 초록 글씨 제거
    checkObj.memberEmail = false; // 유효하지 않다고 체크
    return;
  } else {

    emailMessage.innerText = emailMessageObj.check; // 중복X 메시지
    emailMessage.classList.add("confirm");
    emailMessage.classList.remove("error");
    checkObj.memberEmail = true; // 유효한 이메일임을 기록
  }


});


// -------------------------------------------------------------------------------------------
/* 이메일 인증 */

const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn"); // 인증번호 받기 버튼
const authKeyMessage = document.querySelector("#authKeyMessage"); // 인증 관련 메시지 출력 span

const initTime = "05:00"; // 인증 초기 시간
const initMin = 4;
const initSec = 59;

// 실제 줄어둔 시간을 저장할 변수
let min = initMin; // 분
let sec = initSec; // 초

let authTimer;

sendAuthKeyBtn.addEventListener("click", () => { // 인증 번호 받기 클릭 시

  checkObj.authKey = false; // 인증 안된 상태로 기록
  authKeyMessage.innerText = ""; // 인증 관련 메시지 삭제

  if(authTimer != undefined){
  clearInterval(authTimer); // 이전 인증 타이머 초기화
  }
  
  // 이메일이 유효하지 않은 경우
  if(checkObj.memberEmail === false){
    alert("유효한 이메일을 작성해 주세요")
    return;
  }

  // 서버에서 작성된 이메일로 인증 코드 발송 (Ajax)
  fetch("/email/sendAuthKey2", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : memberEmail.value
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("이메일 발송 실패")
  })
  .then(result => {
    // console.log(result);
  })
  .catch(err => console.error(err));

  // 이메일 발송 메시지 출력 + 5분 타이머 출력

  alert("인증번호가 발송 되었습니다");

  authKeyMessage.innerText = initTime; // 5:00 문자열 출력
  authKeyMessage.classList.remove("confirm", "error"); // 검정 글씨

  // 1초가 지날 때 마다 함수 내부 내용이 실행되는 setInterval을 작성
  authTimer = setInterval(() => {
    authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

    // 0분 0초인 경우
    if(min === 0 && sec === 0){
      checkObj.authKey = false; // 인증 못했다고 기록
      clearInterval(authTimer); // 1초마다 동작하는 setInterval 멈춤
      authKeyMessage.classList.add("error"); // 빨간 글씨
      authKeyMessage.classList.remove("confirm");
      return;
    }


    if(sec === 0 ){ // 출력된 초가 0인 경우 (1분 지남)
      sec = 60;
      min--; // 분 감소
    }

    sec--; // 1초가 지날 때 마다 sec값 1씩 감소

  }, 1000);

});

/* 전달 받은 숫자가 10 미만 (한 자리 수) 인 경우 앞에 0을 붙여서 반환하는 함수 */
function addZero(num){
  if(num < 10) return "0" + num;
  else         return num;
}

/* 인증 번호를 입력하고 인증하기 버튼을 클릭할 경우 */
const authKey = document.querySelector("#authKey")
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn")

checkAuthKeyBtn.addEventListener("click", () => {
  if(min === 0 && sec === 0){
    alert("인증 번호 제한시간 초과");
    return;
  }

  if(authKey.value.trim().length < 6){
    alert("인증 번호가 잘못 입력되었습니다");
    return;
  }
    // 서버로 제출할 데이터
    const obj = {
      "email" : memberEmail.value, // 입력한 이메일
      "authKey" : authKey.value // 입력한 인증 번호
    };


    //Redis에 저장된 이메일, 인증번호와 일치하는지 확인
    fetch("/email/checkAuthKey", {
      method : "POST",
      headers : {"Content-Type" : "application/json"},
      body : JSON.stringify(obj)
    })
    .then(response => {
      if(response.ok) return response.text();
      throw new Error("인증 에러");
    })
    .then(result => {
      console.log("인증 결과 : ", result);
  
      // 3) 일치하지 않는 경우
      if(result == 'false'){
        alert("인증 번호가 일치하지 않습니다");
        checkObj.authKey = false;
        return;
      }
      // 4) 일치하는 경우
      // - 타이머 멈춤
      clearInterval(authTimer);
  
      // + "인증 되었습니다" 화면에 초록색으로 출력
      authKeyMessage.innerText = "인증 되었습니다";
      authKeyMessage.classList.add("confirm");
      authKeyMessage.classList.remove("error");
  
      checkObj.authKey = true; // 인증 완료 표시
    })
    .catch(err => console.error(err));
  
  });

  // 임시 비밀번호 발송

  const sendEmail = document.querySelector("#sendEmail"); // 이메일 발송 버튼

  sendEmail.addEventListener("click", () => { // 이메일 발송 버튼 클릭 시


    
    if(memberEmail.value.trim().length == 0){
      alert("이메일을 입력해주세요")
      return
    }
    
    if(checkObj.memberEmail && checkObj.authKey == false){
      alert("이메일 또는 인증번호를 확인해주세요")
      e.preventDefault();
      return;
    } else {
      alert("인증된 이메일로 임시비밀번호를 발송드렸습니다")
    } 

    // 서버에서 작성된 이메일로 인증 코드 발송 (Ajax)
    fetch("/email/sendAuthKey3", {
      method : "POST",
      headers : {"Content-Type" : "application/json"},
      body : memberEmail.value
    })
    .then(response => {
      if(response.ok) return response.text();
      throw new Error("이메일 발송 실패")
    })
    .then(result => {
      // console.log(result);
      if(result === 1){
      }
    })
    .catch(err => console.error(err));

    
    location.href = "/myPage/loginPage";


  
  
  });