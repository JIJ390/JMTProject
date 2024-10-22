package edu.kh.jmt.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.board.dto.Board;
import edu.kh.jmt.board.mapper.BoardMapper;
import edu.kh.jmt.common.util.FileUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;

	@Value("${my.board.web-path}")
	private String webPath;
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	
	// 게시글 등록
	@Override
	public int boardWrite(Board inputBoard, MultipartFile boardImage) {
		
		
		//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		// ★★★★★★ 파일을 가져와서 이름변경을 하고
		// -> boardDto에 담아서 DB로 넘겨서 insert 하는것 ★★☆★☆★☆★☆
		// -> INSERT가 성공이 된다면???? 실제 파일을 서버에 올린다!
		
		
			
		// 1) 파일 업로드 확인
		if(!boardImage.isEmpty()) { 
				
		// 2) 웹 접근경로(config.properties) + 변경된 파일명
		
			String rename = FileUtil.rename(boardImage.getOriginalFilename());
			
			String url = webPath + rename;
			
			inputBoard.setBoardImg(url);
			
			}

			// 3) 게시글 부분 ( 제목, 내용, 작성자, 게시판 종류 ) INSERT
			int result = mapper.boardWrite(inputBoard);
			System.out.println(inputBoard.getBoardNo());
			
	    // 삽입 실패 시
			if(result == 0) return 0;
			
			/* 삽입된 게시글 번호 */
			int boardNo = inputBoard.getBoardNo();
			
		
	

		
		// 4) DB UPDATE 수행
//				int result = mapper.profile(url, memberNo);
//				
//				if(result == 0) return null; // 업데이트 실패시 null 반환
//				
//				try {
//					// C:/uploadFiles/profile/ 폴더가 없으면 생성
//					File folder = new File(profileFolderPath);
//					if(!folder.exists()) folder.mkdir();
//					
//					// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
//					boardImage.transferTo(
//							new File(profileFolderPath + rename));
		
		
		return 0;
	}
				
	
}
