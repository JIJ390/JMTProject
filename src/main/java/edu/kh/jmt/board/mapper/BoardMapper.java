package edu.kh.jmt.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.board.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시글 등록
	 * @param inputBoard
	 * @return
	 */
	int boardWrite(Board inputBoard);

	/** 게시글 출력
	 * @return
	 */
	List<Board> boardMain();

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
