package codegurus.board;

import codegurus.board.vo.ReqBoardDetailVO;
import codegurus.board.vo.ReqBoardListVO;
import codegurus.board.vo.ResBoardDetailVO;
import codegurus.board.vo.ResBoardListElemVO;

import java.util.List;

/**
 * 게시판 DAO
 *
 * @author 이미란
 * @version 2021.07
 */
public interface BoardDAO {

    List<ResBoardListElemVO> selectBoardList(ReqBoardListVO reqVo);
    ResBoardDetailVO selectBoardDetail(ReqBoardDetailVO reqVo);
}
