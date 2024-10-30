package edu.kh.jmt.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.board.dto.Board;
import edu.kh.jmt.board.dto.BoardPagination;
import edu.kh.jmt.board.mapper.BoardMapper;
import edu.kh.jmt.common.util.FileUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;

	@Value("${my.board.web-path}")
	private String webPath;

	@Value("${my.board.folder-path}")
	private String folderPath;

	// 게시글 등록
	@Override
	public int boardWrite(Board inputBoard, MultipartFile boardImage) {

		// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		// ★★★★★★ 파일을 가져와서 이름변경을 하고
		// -> boardDto에 담아서 DB로 넘겨서 insert 하는것 ★★☆★☆★☆★☆
		// -> INSERT가 성공이 된다면???? 실제 파일을 서버에 올린다!

		String rename = null;

		// 1) 파일 업로드 확인
		if (!boardImage.isEmpty()) {

			// 2) 웹 접근경로(config.properties) + 변경된 파일명

			rename = FileUtil.rename(boardImage.getOriginalFilename());

			String url = webPath + rename;

			inputBoard.setBoardImg(url);

		}

		// 3) 게시글 부분 ( 제목, 내용, 작성자, 게시판 종류 ) INSERT
		int result = mapper.boardWrite(inputBoard);

		System.out.println(inputBoard.getBoardNo());

		// 삽입 실패 시
		if (result == 0)
			return 0;

		/* 삽입된 게시글 번호 */
		int boardNo = inputBoard.getBoardNo();

		// 4) DB UPDATE 수행

		// 파일 없을 시 업로드 하지 않고 리턴
		if (boardImage.isEmpty()) {
			return boardNo;
		}

		try {
			// C:/uploadFiles/board2/ 폴더가 없으면 생성
			File folder = new File(folderPath);
			if (!folder.exists())
				folder.mkdir();

			// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
			boardImage.transferTo(new File(folderPath + rename));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return boardNo;
	}

	// 메인페이지 출력
	@Override
	public Map<String, Object> boardMain(int cp) {

		int listCount = mapper.getBoardListCount();

		BoardPagination pagination = new BoardPagination(cp, listCount);

		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;

		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Board> boardList = mapper.selectBoardList(rowBounds);

		Map<String, Object> map = Map.of("boardList", boardList, "pagination", pagination);

		return map;
	}
	
	// 검색 시 공지 리스트 
	@Override
	public Map<String, Object> searchBoardMain(int cp, Map<String, Object> paramMap) {
		
		int searchCount = mapper.searchBoardCount(paramMap);

		BoardPagination boardPagination = new BoardPagination(cp, searchCount);
		
		int limit = boardPagination.getLimit(); // 10
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.searchBoardList(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put(("boardList"), boardList);
		map.put(("pagination"), boardPagination);		
		
		return map;
	}
	

	// 게시글 삭제
	@Override
	public int boardDelete(int boardNo, int memberNo) {
		return mapper.boardDelete(boardNo, memberNo);
	}

	// 게시글 수정
	@Override
	public Board updateView(int boardNo) {
		return mapper.updateView(boardNo);
	}

	// 게시글 수정 2
	@Override
	public int boardUpdate(Board inputBoard, MultipartFile boardImage) {

		String rename = null;

		// 파일 존재 시 이름 바꾸기
		if (!boardImage.isEmpty()) {

			rename = FileUtil.rename(boardImage.getOriginalFilename());

			String url = webPath + rename;

			inputBoard.setBoardImg(url);

		}

		System.out.println(inputBoard);
		System.out.println(inputBoard);
		System.out.println(inputBoard);

		int result = mapper.boardUpdate(inputBoard);

		try {
			// C:/uploadFiles/board2/ 폴더가 없으면 생성
			File folder = new File(folderPath);
			if (!folder.exists())
				folder.mkdir();

			// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
			boardImage.transferTo(new File(folderPath + rename));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 게시글 신고
	@Override
	public String boardReport(String reportContent, String reportType) {
		return mapper.boardReport(reportContent, reportType);
	}
	
}
