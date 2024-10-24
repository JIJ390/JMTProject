package edu.kh.project.common.exception;

// 사용자 정의 예외 만드는 방법
// -> 아무 Exception 클래스를 상속 받으면 된다!
//	Checked Exception 상속 -> Checked 사용자 정의 예외
//	UnChecked Exception 상속 -> UnChecked 사용자 정의 예외
public class FileUploadFailException extends RuntimeException {
	
	public FileUploadFailException() {
		super("파일 업로드 실패");
	}
	
	public FileUploadFailException(String message) {
		super(message);
	}
}
