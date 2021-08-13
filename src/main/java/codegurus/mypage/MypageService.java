package codegurus.mypage;

import codegurus.cmm.util.SystemUtil;
import codegurus.cmm.vo.res.Res;
import codegurus.learning.vo.BookVO;
import codegurus.mypage.vo.ReqBookcaseListVO;
import codegurus.mypage.vo.ReqMagnitudeListVO;
import codegurus.mypage.vo.ResBookcaseListVO;
import codegurus.mypage.vo.ResMagnitudeListVO;
import codegurus.schedule.vo.ResScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
