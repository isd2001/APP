package codegurus.board;

import codegurus.board.vo.*;

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

    ResBoardDetailVO selectBoardOne(ReqBoardOneVO reqVo);

    int updateLookupCount(ReqBoardDetailVO reqVo);

    int updatePromotionAgree(ReqPromotionAgreeVO reqVo);

    ClientVersionVO SelectClientVersionCheck(ReqClientVersionCheckVO reqVo);
}
