/* 회원 가입 유효성 검사 */

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
emailMessageObj.duplication = "이미 사용중인 이메일 입니다";
emailMessageObj.check = "사용 가능한 이메일 입니다";

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
  }


  // 이메일 중복 검사(AJAX)
  fetch("/myPage/emailCheck?email=" + inputEmail)
  .then(response => {
    if(response.ok){ // HTTP 응답 상태 코드 200번(응답 성공)
      return response.text(); // 응답 결과를 text로 파싱
    }

    throw new Error("이메일 중복 검사 에러");
  })
  .then(count => {
    // 매개 변수 count : 첫 번째 then에서 return된 값이 저장된 변수

    if(count == 1){ // 중복인 경우
      emailMessage.innerText = emailMessageObj.duplication; // 중복 메시지
      emailMessage.classList.add("error");
      emailMessage.classList.remove("confirm");
      checkObj.memberEmail = false;
      return;
    } 

    // 중복이 아닌 경우
    emailMessage.innerText = emailMessageObj.check; // 중복X 메시지
    emailMessage.classList.add("confirm");
    emailMessage.classList.remove("error");
    checkObj.memberEmail = true; // 유효한 이메일임을 기록

  })
  .catch( err => console.error(err) );

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
  fetch("/email/sendAuthKey", {
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
      console.log("인증 결과 :", result);

      if(result == false){ // 인증 실패
        alert("인증 번호가 일치하지 않습니다")
        checkObj.authKey = false;
        return;
      } else{ // 인증 성공
        clearInterval(authTimer);

        // 인증 멘트 화면에 표시
        authKeyMessage.innerText = "인증 되었습니다";
        authKeyMessage.classList.add("confirm");
        authKeyMessage.classList.remove("error");

        checkObj.authKey = true; // 인증 완료 표시
      }

    })
    .catch(err => console.error(err));
});

/* 이름 유효성 검사 */
const memberName = document.querySelector("#memberName")
const nameMessage = document.querySelector("#nameMessage")

// 이름 관련 표시 메시지
const nameMessageObj = {};
nameMessageObj.normal = "한글,영어,숫자로만 3~10글자";
nameMessageObj.invaild = "유효하지 않은 이름 형식 입니다";
nameMessageObj.duplication = "이미 사용중인 이름 입니다";
nameMessageObj.check = "사용 가능한 이름 입니다";

memberName.addEventListener("input", () =>{

  // 입력 받은 이름
  const inputName = memberName.value.trim();

  // 입력된 이름이 없을 경우
  if(inputName.length == 0){
    nameMessage.innerText = nameMessageObj.normal;
    nameMessage.classList.remove("confirm", "error");
    checkObj.memberName = false;
    memberName.value = "";
    return;
  }


  // 이름 유효성 검사(정규 표현식)
  const regEx = /^[a-zA-Z0-9가-힣]{3,10}$/; // 한글,영어,숫자로만 3~10글자

  if(regEx.test(inputName) == false){
    nameMessage.innerText = nameMessageObj.invaild;
    nameMessage.classList.remove("confirm");
    nameMessage.classList.add("error");
    checkObj.memberName = false;
    return;
  }

  // 이름 중복 검사
  fetch("/myPage/nameCheck?memberName=" + inputName)
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("닉네임 중복 검사 에러");
  })
  .then(count => {
    if(count == 1){
      nameMessage.innerText = nameMessageObj.duplication;
      nameMessage.classList.remove("confirm");
      nameMessage.classList.add("error");
      checkObj.memberName = false;
      return;
    }
    nameMessage.innerText = nameMessageObj.check;
    nameMessage.classList.remove("error");
    nameMessage.classList.add("confirm");
    checkObj.memberName = true;
  })
  .catch(err => console.error(err));

})


/* 비밀번호 유효성 검사 */
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

const pwMessageObj = {};
pwMessageObj.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이.";
pwMessageObj.invaild = "유효하지 않은 비밀번호 형식입니다.";
pwMessageObj.vaild = "유효한 비밀번호 형식입니다.";
pwMessageObj.error = "비밀번호가 일치하지 않습니다.";
pwMessageObj.check = "비밀번호가 일치 합니다.";


memberPw.addEventListener("input", () => {
  
  const inputPw = memberPw.value.trim();

  if(inputPw.length === 0){ // 비밀번호 미입력
    pwMessage.innerText = pwMessageObj.normal;
    pwMessage.classList.remove("confirm", "error");
    checkObj.memberPw = false;
    memberPw.value = "";
    return;
  }

  // 비밀번호 정규표현식 검사
  const lengthCheck = inputPw.length >= 6 && inputPw.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(inputPw); // 영어 알파벳 포함
  const numberCheck = /\d/.test(inputPw); // 숫자 포함
  const specialCharCheck = /[\!\@\#\_\-]/.test(inputPw); // 특수문자 포함

  // 조건이 하나라도 만족하지 못하면
  if ( !(lengthCheck && letterCheck && numberCheck && specialCharCheck) ) {
    pwMessage.innerText = pwMessageObj.invaild;
    pwMessage.classList.remove("confirm");
    pwMessage.classList.add("error");
    checkObj.memberPw = false;
    return;
  }


  pwMessage.innerText = pwMessageObj.vaild;
  pwMessage.classList.remove("error");
  pwMessage.classList.add("confirm");
  checkObj.memberPw = true;


  // 비밀번호 확인이 작성된 상태에서
  // 비밀번호가 입력된 경우
  if(memberPwConfirm.value.trim().length > 0){
    checkPw(); // 같은지 비교하는 함수 호출
  }

});


/* ----- 비밀번호, 비밀번호 확인 같은지 검사하는 함수 ----- */
function checkPw(){

  // 같은 경우
  if(memberPw.value === memberPwConfirm.value){
    pwMessage.innerText = pwMessageObj.check;
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPwConfirm = true;
    return;
  }

  // 다른 경우
  pwMessage.innerText = pwMessageObj.error;
  pwMessage.classList.add("error");
  pwMessage.classList.remove("confirm");
  checkObj.memberPwConfirm = false;
}


/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
memberPwConfirm.addEventListener("input", () => {

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if(checkObj.memberPw === true){
    checkPw();
    return;
  }

  // 비밀번호 input에 작성된 값이 유효하지 않은 경우
  checkObj.memberPwConfirm = false;
});




/* 회원가입 form 제출 시 전체 유효성 검사 여부 확인 */
const signUpForm = document.querySelector("#signUpForm");

signUpForm.addEventListener("submit", e => {

  for(let key in checkObj){

    if(checkObj[key] === false){ // 유효하지 않은 경우
      let str; // 출력할 메시지 저장
      switch(key){
        case "memberEmail"     : str = "이메일이 유효하지 않습니다"; break;
        case "memberPw"        : str = "비밀번호가 유효하지 않습니다"; break;
        case "memberPwConfirm" : str = "비밀번호 확인이 일치하지 않습니다"; break;
        case "memberName"      : str = "이름이 유효하지 않습니다"; break;
        case "authKey"         : str = "이메일이 인증되지 않았습니다"; break;
      }

      alert(str); // 경고 출력

      document.getElementById(key).focus();

      e.preventDefault();

      return;
    }
  }
});
