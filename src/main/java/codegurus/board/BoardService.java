package codegurus.board;

import codegurus.board.vo.*;
import codegurus.cmm.util.SystemUtil;
import codegurus.cmm.vo.res.Res;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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

        List<ResBoardListElemVO> list = boardDAO.selectBoardList(reqVo);
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
        boardDAO.updateLookupCount(reqVo);

        ResBoardDetailVO resVo = boardDAO.selectBoardDetail(reqVo);

        if(resVo == null) {
            SystemUtil.returnNoSearchResult();
        } // 조회결과 없음 리턴

        return resVo;
    }

    /**
     * 이용약관, 개인정보, 마케팅활용 동의 조회
     *
     * @param reqVo
     * @return
     */
    public ResBoardDetailVO selectBoardOne(ReqBoardOneVO reqVo) {

        ResBoardDetailVO resVo = boardDAO.selectBoardOne(reqVo); // 이용약관, 개인정보, 마케팅활용 동의 조회

        if(resVo == null) {
            SystemUtil.returnNoSearchResult();
        } // 조회결과 없음 리턴

        return resVo;
    }

    /**
     * 마케팅활용 동의
     *
     * @param reqVo
     * @param resVo
     * @return
     */
    public void promotionAgree(ReqPromotionAgreeVO reqVo, ResPromotionAgreeVO resVo) {

        int updated =boardDAO.updatePromotionAgree(reqVo);

        SystemUtil.checkUpdatedCount(updated, 1);
        resVo.setResMsg("마케팅활용 동의 성공");
    }

    /**
     * 클라이언트 버전 체크
     *
     * @param reqVo
     * @pamam resVo
     * @return
     */
    public ResClientVersionCheckVO clientVersionChcek(ReqClientVersionCheckVO reqVo, ResClientVersionCheckVO resVo) {

        ClientVersionVO item = boardDAO.SelectClientVersionCheck(reqVo);

        if(item == null) {
            SystemUtil.returnNoSearchResult();
        } // 조회결과 없음 리턴
        resVo.setItem(item);
        return resVo;
    }
}
