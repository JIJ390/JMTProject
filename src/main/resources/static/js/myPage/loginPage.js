// 쿠키에 저장된 여러 값 중 key가 일치하는 vlaue 반환
function getCookie(key){

  const cookies = document.cookie; 
  // console.log(cookies);

  const arr = cookies.split(";"); 
  // console.log(arr);

  const cookieobj = {};
  for(let entry of arr){
    const temp = entry.split("="); 

    cookieobj[temp[0]] = temp[1];
  }

  // console.log(cookieobj);

  return cookieobj[key];
}

document.addEventListener("DOMContentLoaded", () => {
  const saveEmail = getCookie("saveEmail");

  if(saveEmail == undefined) return; // 저장된 이메일이 없을 경우

  const loginEmail = document.querySelector("#loginPage input[name=loginEmail]");
  const checkbox = document.querySelector("#loginPage input[name=saveEmail]");

  loginEmail.value = saveEmail;

  checkbox.checked = true;
})
