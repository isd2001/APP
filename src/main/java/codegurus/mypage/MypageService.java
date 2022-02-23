package codegurus.mypage;

import codegurus.auth.AuthDAO;
import codegurus.auth.vo.*;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.constants.ResCodeEnum;
import codegurus.cmm.exception.CustomException;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.SystemUtil;
import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.ContentsHistoryVO;
import codegurus.mypage.vo.*;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
	private EgovEnvCryptoService cryptoService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AuthDAO authDAO;

    /**
     * 나의 진도 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResMagnitudeListVO selectmagnitudeList(ReqMagnitudeListVO reqVo) {
        ResMagnitudeListVO resVo = new ResMagnitudeListVO();

        List<ContentsHistoryVO> list = mypageDAO.selectmagnitudeList(reqVo);

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

        UserVO userVO = cacheService.getTokenUser(reqVo.getProductId());
        if(userVO == null){ SystemUtil.returnNoSearchResult(); }

        resVo.setUsername(userVO.getUsername());
        resVo.setName(userVO.getName());
        resVo.setGender(userVO.getGender());
        // resVo.setBirth(DateUtil.convertDateFormat(vo.getBirth(), Constants.DF8, Constants.DF8_HAN_NO_ZEROS)); // 화면에 출력하는 건 한글이 좋은데, 수정UI에서 datepicker가 없는 이상 혼선이 예상되므로 DF8을 사용함.
        resVo.setBirth(userVO.getBirth());
        resVo.setAuthCode(userVO.getAuthCode());
        resVo.setTrialEndDate(userVO.getTrialEndDate());
        resVo.setPromotionAgreeOrnot(userVO.getPromotionAgreeOrnot());
        resVo.setAppPushAgreeOrnot(userVO.getAppPushAgreeOrnot());

        if(userVO.getUsername().equals(TokenProvider.TRIAL_USER)) {
            // 체험회원 정보 조회
            resVo.setParentName(userVO.getParentName());
            resVo.setParentBirth(userVO.getParentBirth());
        } else {
            // 부모 회원정보 조회
            Map<String, String> parentInfo = mypageDAO.selectParentInfo(reqVo);
            if(parentInfo != null){
                resVo.setParentName(parentInfo.get("parent_cust_nm"));
                String parentBirth = parentInfo.get("parent_birthdt");
                if(StringUtil.isNotBlank(parentBirth)){
                    resVo.setParentBirth(parentBirth);
                }
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

        // 생일을 바꾸면 스케줄 정보도 바꿔야할수있다..
        UserVO userVO = cacheService.getTokenUser(ProductEnum.상품_스마트독서.getProductId());

        String currentBirthYear = userVO.getBirth();

        if(currentBirthYear == null) currentBirthYear = "2022";
        else currentBirthYear = currentBirthYear.substring(0, 4);

        int updated = mypageDAO.updateUserInfo(reqVo);
        SystemUtil.checkUpdatedCount(updated, 1);

        String updateBirthYear = reqVo.getBirth();

        if(updateBirthYear == null) updateBirthYear = "2022";
        else updateBirthYear = updateBirthYear.substring(0, 4);

        if(!currentBirthYear.equals(updateBirthYear)) {
            // 스케줄을 수정할수없도록 보정해줘야함. 수정하는 연도가 높아진다는것은 나이가 어려지는것 + 반대는 -
            int gap = (Integer.parseInt(updateBirthYear) - Integer.parseInt(currentBirthYear)) * 12;

            ScheduleIntervalVO scheduleIntervalVO = new ScheduleIntervalVO();
            scheduleIntervalVO.setUserManageId(reqVo.getUserManageId());
            scheduleIntervalVO.setProductId(ProductEnum.상품_스마트독서.getProductId());
            int currentScheduleIntervalValue = authDAO.selectScheduleInterval(scheduleIntervalVO);
            authDAO.deleteScheduleInterval(scheduleIntervalVO);
            scheduleIntervalVO.setValue(currentScheduleIntervalValue + gap);
            authDAO.insertScheduleInterval(scheduleIntervalVO);

            scheduleIntervalVO.setProductId(ProductEnum.상품_플라톤.getProductId());
            currentScheduleIntervalValue = authDAO.selectScheduleInterval(scheduleIntervalVO);
            authDAO.deleteScheduleInterval(scheduleIntervalVO);
            scheduleIntervalVO.setValue(currentScheduleIntervalValue + gap);
            authDAO.insertScheduleInterval(scheduleIntervalVO);
        }

        return vo;
    }

    /**
     * 회원정보 > 패스워드 변경
     *
     * @param reqVo
     * @return
     */
    public ResBaseVO updatePW(ReqUpdatePWVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        String passwordRaw = reqVo.getPassword();
        reqVo.setPasswordMask(cryptoService.encrypt(StringUtil.maskPW(passwordRaw))); // 패스워드 마스킹
        reqVo.setPassword(passwordEncoder.encode(passwordRaw)); // 패스워드 해싱

        int updated = mypageDAO.updateUserPassword(reqVo);
        SystemUtil.checkUpdatedCount(updated, 1);

        return resVo;
    }

    /**
     * 회원정보 > 계정 삭제 > 패스워드 확인
     *
     * @param reqVo
     * @return
     */
    public ResBaseVO makeSurePW(ReqUpdatePWVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        // 사용자정보 조회
        Map<String, String> params = new HashMap<>();
        params.put("userManageId", reqVo.getUserManageId());
        params.put("productId", ProductEnum.상품_스마트독서.getProductId());
        UserVO userVo = commonDAO.selectUserByUserManageId(params);
        if (userVo == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        // 패스워드 대조
        boolean match = passwordEncoder.matches(StringUtil.trim(reqVo.getPassword()), userVo.getPassword());
        log.debug("## 패스워드 대조 결과:[{}]", match);
        if (! match) {
            throw new CustomException(ResCodeEnum.INFO_0010);
        }

        return resVo;
    }

    /**
     * 회원정보 > 계정 삭제
     *
     * @param reqVo
     * @return
     */
    public ResBaseVO deleteUser(ReqDeleteUserVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        // 사용자정보 조회
        Map<String, String> params = new HashMap<>();
        params.put("userManageId", reqVo.getUserManageId());
        params.put("productId", ProductEnum.상품_스마트독서.getProductId());
        UserVO userVo = commonDAO.selectUserByUserManageId(params);
        if (userVo == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        // 패스워드 대조
        boolean match = passwordEncoder.matches(StringUtil.trim(reqVo.getPassword()), userVo.getPassword());
        log.debug("## 패스워드 대조 결과:[{}]", match);
        if (! match) {
            throw new CustomException(ResCodeEnum.INFO_0010);
        }

        int updated = mypageDAO.deleteUser(reqVo.getUserManageId());
        SystemUtil.checkUpdatedCount(updated, 1);

        return resVo;
    }
}