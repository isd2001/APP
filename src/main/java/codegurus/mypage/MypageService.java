package codegurus.mypage;

import codegurus.cmm.util.SystemUtil;
import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.ContentsHistoryVO;
import codegurus.mypage.vo.*;
import codegurus.schedule.vo.ResScheduleVO;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *  마이페이지 서비스
 *
 * @author 이미란
 * @version 2021.09
 */
@Slf4j
@Service
public class MypageService {

    @Autowired
    private MypageDAO mypageDAO;

    /**
     * 나의 진도 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResMagnitudeListVO selectmagnitudeList(ReqMagnitudeListVO reqVo) {
        ResMagnitudeListVO resVo = new ResMagnitudeListVO();

        List<ResScheduleVO> list = mypageDAO.selectmagnitudeList(reqVo);

        if (list.size() == 0) {
            SystemUtil.returnNoSearchResult();
        } // 조회 결과 없음 리턴

        resVo.setList(list);

        return resVo;
    }

    /**
     * 나의 책장 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResBookcaseListVO selectBookcaseList(ReqBookcaseListVO reqVo) {
        ResBookcaseListVO resVo = new ResBookcaseListVO();

        List<BookVO> list = mypageDAO.selectBookcaseList(reqVo);

        if (list.size() == 0) {
            SystemUtil.returnNoSearchResult();
        } // 조회 결과 없음 리턴

        resVo.setList(list);

        return resVo;
    }

    /**
     * 나의 사전 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResDicListVO selectDicList(ReqDicListVO reqVo) {

        ResDicListVO resVo = new ResDicListVO();

        // 나의 사전 목록 조회
        List<ContentsHistoryVO> list = mypageDAO.selectDicList(reqVo);
        if (list == null) {
            SystemUtil.returnNoSearchResult();
        } // 조회 결과 없음 리턴

        resVo.setList(list);

        return resVo;
    }

    /**
     * 포트폴리오 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResPortfolioListVO selectportfolioList(ReqPortfolioListVO reqVo) {
        ResPortfolioListVO resVo = new ResPortfolioListVO();

        List<BookVO> list = mypageDAO.selectportfolioList(reqVo);

        if (list.size() == 0) {
            SystemUtil.returnNoSearchResult();
        } // 조회 결과 없음 리턴

        resVo.setList(list);

        return resVo;
    }
}