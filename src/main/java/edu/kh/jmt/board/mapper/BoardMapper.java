package edu.kh.jmt.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;


import edu.kh.jmt.board.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시글 등록
	 * @param inputBoard
	 * @return
	 */
	int boardWrite(Board inputBoard);
	
	/**
	 * 전체 게시글의 개수
	 * @return
	 */
	int getBoardListCount();
	
	/**
	 * 전체 게시글 목록 조회
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectBoardList(RowBounds rowBounds);
	
	
	
	/**
	 * 검색 게시글 개수 조회
	 * @param paramMap
	 * @return
	 */
	int searchBoardCount(Map<String, Object> paramMap);
	
	
	/**
	 * 검색된 게시글 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Board> searchBoardList(Map<String, Object> paramMap, RowBounds rowBounds);



	/** 게시글 삭제
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int boardDelete(
			@Param("boardNo")	int boardNo, 
			@Param("memberNo")	int memberNo);

  /** 게시글 수정
   * @param boardNo
   * @return
   */
	Board updateView(int boardNo);

	/** 게시글 수정2
	 * @param boardNo 
	 * @param inputBoard
	 * @param boardImg
	 * @return
	 */
	int boardUpdate(Board inputBoard);



	





}
