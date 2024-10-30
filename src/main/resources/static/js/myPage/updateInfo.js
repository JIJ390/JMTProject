let statusCheck = -1; // 프로필 이미지 업로드 상태에 따라서 어떤 상태인지 구분하는 값
                      // -1 : 프로필 이미지를 바꾼적이 없음(초기상태)
                      //  0 : 프로필 이미지 삭제(X버튼 클릭)
                      //  1 : 새 이미지 선택

let lastVailidFile = null; // 마지막으로 선택된 파일을 저장할 변수

// 미리보기가 출력될 img
const profileImg = document.querySelector("#profileImg");
// 프로필 이미지를 선택할 input
const imageInput = document.querySelector("#imageInput");
// 기본 이미지로 변경할 x 버튼
const deleteImage = document.querySelector("#deleteImage");

if(imageInput !== null){ // 프로필 변경 화면인 경우

  /** 미리 보기 함수
   * @param {*} file : input type="file" 에서 선택된 파일 
   */
  const updatePreview = (file) => {

    lastVailidFile = file; // 선택된 파일을 lastVailidFile에 대입(복사)
    
    // JS에서 제공하는 파일을 읽어오는 객체
    const reader = new FileReader();

    // 파일을 읽어오는데 DataURL 형식으로 읽어옴
    // DataURL : 파일 전체 데이터가 브라우저가 해석할 수 있는 긴 주소형태 문자열로 변환
    reader.readAsDataURL(file);


    // 선택된 파일이 다 인식 되었을 때
    reader.addEventListener("load", e => {
      profileImg.src = e.target.result;
      // e.target.result == 파일이 변환된 주소 형태 문자열

      // 로컬 스토리지에 이미지 URL 저장
      localStorage.setItem("updateProfileImg", e.target.result);

      statusCheck = 1; // 새 파일이 선택된 상태 체크

    })
  }

  /* input type="file" 태그가 선택한 값이 변한 경우 실행 */
  imageInput.addEventListener("change", e => {

    // 선택된 파일 1개를 얻어옴
    const file = e.target.files[0];

    // 선택된 파일이 없을 경우
    if(file === undefined){

      /* 이전 선택한 파일 유지하는 코드 */
      // -> 이전 선택한 파일을 저장할 전역 변수(lastVailidFile) 선언

      // 이전에 선택한 파일이 "없는" 경우 == 현재 페이지 들어와서 프로필 이미지 바꾼적이 없는 경우
      if(lastVailidFile === null) return;

      // 이전에 선택한 파일이 "있을" 경우
      const dataTransfer = new DataTransfer();

      // dataTransfer가 가지고 있는 files 필드에 lastVailidFile 추가
      dataTransfer.items.add(lastVailidFile);

      // input의 files 변수에 lastVailidFile이 추가된 files 대입
      imageInput.files = dataTransfer.files;

      // 이전 선택된 파일로 미리보기 되돌리기
      updatePreview(lastVailidFile);

      return;
    }

    // 선택된 파일이 있을 경우
    updatePreview(file); // 미리보기 함수 호출
  })
}

/* X버튼 클릭 시 기본 이미지로 변환 */
deleteImage.addEventListener("click", () => {
    
  // 미리보기를 기본 이미지로 변경
  profileImg.src = userDefaultImage;

  // input 태그와 마지막 선택된 파일을 저장하는 lastValidFile에 저장된 값을 모두 삭제
  imageInput.value = '';
  lastVailidFile = null;

  statusCheck = 0; // 삭제 상태 체크
})

// 로컬 스토리지에 저장된 파일 복구
window.addEventListener("DOMContentLoaded", () => {
  const savedImageUrl = localStorage.getItem("updateProfileImg");

  if (savedImageUrl) {
    profileImg.src = savedImageUrl;

    // 복구된 이미지가 있는 경우 statusCheck를 1로 설정
    statusCheck = 1; // 이미지가 선택된 상태로 초기화
  } else {
    profileImg.src = userDefaultImage; // 기본 이미지 사용
    statusCheck = -1; // 이미지가 선택되지 않은 초기 상태로 설정
  }
});

// 프로필 화면에서 변경하기 버튼이 클릭된 경우
const profileForm = document.querySelector("#updateInfoForm");
profileForm?.addEventListener("submit", (e) => {
  let flag = false; // 기본적으로 제출이 가능하다고 가정

  // 미변경 시 제출 불가
  if (statusCheck === -1) {
    flag = true; // 변경이 없을 때 제출 불가
  }

  // 기존 프로필 이미지가 없고 새 이미지가 선택된 경우
  if (loginMemberProfileImg === null && statusCheck === 1) {
    flag = false; // 제출 가능
  }

  // 기존 프로필 이미지가 있고 X 버튼을 눌러 삭제한 경우
  if (loginMemberProfileImg !== null && statusCheck === 0) {
    flag = false; // 제출 가능
  }

  // 기존 프로필 이미지가 있고 새 이미지를 선택한 경우
  if (loginMemberProfileImg !== null && statusCheck === 1) {
    flag = false; // 제출 가능
  }

  if (flag) {
    e.preventDefault(); // 제출을 막음
    alert("프로필 이미지에 변경사항이 없습니다.");
  }
});
