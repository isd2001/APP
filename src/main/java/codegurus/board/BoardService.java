package codegurus.board;

import codegurus.board.vo.*;
import codegurus.cmm.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  게시판 서비스
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardDAO boardDAO;

    /**
     * 게시판 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResBoardListVO selectBoardList(ReqBoardListVO reqVo) {
        ResBoardListVO resVo = new ResBoardListVO();

        List<ResBoardListElemVO> list = boardDAO.selectBoardList(reqVo); // 공지사항 목록 조회
        if(list.size() == 0) {
            SystemUtil.returnNoSearchResult();
        } // 조회결과 없음 리턴

        resVo.setList(list);

        return resVo;
    }

    /**
     * 게시판 상세 조회
     *
     * @param reqVo
     * @return
     */
    public ResBoardDetailVO selectBoardDetail(ReqBoardDetailVO reqVo) {
        ResBoardDetailVO resVo = boardDAO.selectBoardDetail(reqVo); // 공지사항 목록 조회

        if(resVo == null) {
            SystemUtil.returnNoSearchResult();
        } // 조회결과 없음 리턴

        return resVo;
    }
}
