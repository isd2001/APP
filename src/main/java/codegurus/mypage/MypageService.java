package codegurus.mypage;

import codegurus.auth.vo.UserVO;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.constants.Constants;
import codegurus.cmm.util.DateUtil;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.SystemUtil;
import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.ContentsHistoryVO;
import codegurus.mypage.vo.*;
import codegurus.schedule.vo.ResScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private CommonDAO commonDAO;

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

    /**
     * 회원정보 > 회원정보 조회
     *
     * @param reqVo
     * @return
     */
    public ResUserInfoVO selectUserInfo(ReqUserInfoVO reqVo) {

        ResUserInfoVO resVo = new ResUserInfoVO();

        // 회원정보(학생) 조회
        UserVO vo = commonDAO.selectUserByUserManageId(reqVo.getUserManageId());
        if(vo == null){ SystemUtil.returnNoSearchResult(); }
        resVo.setUsername(vo.getUsername());
        resVo.setName(vo.getName());
        // resVo.setBirth(DateUtil.convertDateFormat(vo.getBirth(), Constants.DF8, Constants.DF8_HAN_NO_ZEROS)); // 화면에 출력하는 건 한글이 좋은데, 수정UI에서 datepicker가 없는 이상 혼선이 예상되므로 DF8을 사용함.
        resVo.setBirth(vo.getBirth());

        // 부모 회원정보 조회
        Map<String, String> parentInfo = mypageDAO.selectParentInfo(reqVo);
        if(parentInfo != null){
            resVo.setParentName(parentInfo.get("parent_cust_nm"));
            String parentBirth = parentInfo.get("parent_birthdt");
            if(StringUtil.isNotBlank(parentBirth)){
                resVo.setParentBirth(parentBirth);
            }
        }

        return resVo;
    }

    /**
     * 회원정보 > 회원정보 수정
     *
     * @param reqVo
     * @return
     */
    public ResBaseVO updateUserInfo(ReqUserUpdateVO reqVo) {

        ResBaseVO vo = new ResBaseVO();
        int updated = mypageDAO.updateUserInfo(reqVo);
        SystemUtil.checkUpdatedCount(updated, 1);

        return vo;
    }
}