const boardImgPreview = document.querySelector(".board-img-preview");
const imageInput = document.querySelector(".imageInput");
const deleteImage = document.querySelector("#deleteImage");

let lastValidFile = null; // 선택 취소 대비 백업용 변수

/**
 * 이미지 미리보기
 * @param {*} file 
 */
const updatePreview = (file) => {

  lastValidFile = file; // 백업

  // JS에서 제공하는 파읽을 읽어오는 객체
  const reader = new FileReader();

  // 이미지 읽어오기(URL 형식으로 변환)
  reader.readAsDataURL(file);

  reader.addEventListener("load", e => {
    boardImgPreview.src = e.target.result;
    // e.target.result == 파일이 변환된 주소 형태 문자열
  })
}

/* 파일이 선택된 경우 */
imageInput.addEventListener("change", e => {
  const file = e.target.files[0];
  updatePreview(file);
})


/** X 버튼 클릭 시 미리보기, 선택된 파일 삭제 */
deleteImage.addEventListener("click", () => {

  boardImgPreview.src = ""; // 미리보기 삭제
  imageInput.value    = ""; // 선택된 파일 삭제
  lastValidFile       = null; // 백업 파일 삭제
})


/** 제목, 내용 미작성시 제출불가 */
const form = document.querySelector("#boardWriteFrm");
form.addEventListener("submit", e =>{

  // 제목, 내용 input 얻어오기
  const boardTitle = document.querySelector("[name=boardTitle]");
  const boardContent = document.querySelector("[name=boardContent]");

  if(boardTitle.value.trim().length === 0){
    alert("제목을 작성해주세요");
    boardTitle.focus();
    e.preventDefault;
    return;
  }

  if(boardContent.value.trim().length === 0){
    alert("내용을 작성해주세요");
    boardContent.focus();
    e.preventDefault();
    return;
  }



})