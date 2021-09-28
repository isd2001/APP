package codegurus.schedule;

import codegurus.auth.AuthDAO;
import codegurus.auth.vo.UserVO;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.service.FileService;
import codegurus.cmm.util.JsonUtil;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.SystemUtil;
import codegurus.schedule.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 도서 일정 서비스
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private FileService fileService;

    @Autowired
    private ScheduleDAO scheduleDAO;

    @Autowired
    private AuthDAO authDAO;

    @Autowired
    private CacheService cacheService;

    /**
     * 온라인 과목 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResSubjectListVO selectSubjectList(ReqSubjectListVO reqVo) {

        ResSubjectListVO resVo = new ResSubjectListVO();
        resVo.setList(scheduleDAO.selectSubjectList(reqVo));

        return resVo;
    }

    /**
     * 도서 일정 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResScheduleListVO selectBookScheduleList(ReqScheduleListVO reqVo) {

        ResScheduleListVO resVo = new ResScheduleListVO();
        resVo.setList(scheduleDAO.selectBookScheduleList(reqVo));

        return resVo;
    }

    /**
     * 이달의 도서 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResScheduleListVO selectThisMonthBookList(ReqThisMonthBookVO reqVo) {

        ResScheduleListVO resVo = new ResScheduleListVO();

        //------------------- 오프라인 진도를 반영한 온라인과목/월 조회 - start -------------------
        int onlineSubjectId = 0;
        String month = "";
        List<String> eduCntrIdList = authDAO.selectUserSubjectList(reqVo.getUserManageId());
//        log.debug("## eduCntrIdList:{}", JsonUtil.toJson(eduCntrIdList));
        for(String eduCntrId : eduCntrIdList){
            Map<String, Object> qp = new HashMap<>();
            qp.put("intgEduCntrId", eduCntrId);
            authDAO.callGetOnlineSubjInfo(qp);
            log.debug("## qp:{}", JsonUtil.toJson(qp));
            String productId = StringUtil.trim(qp.get("productId"));
            if(ProductEnum.상품_스마트독서.getProductId().equals(productId)){
                onlineSubjectId = (int)qp.get("onlineSubjectId");
                month = StringUtil.trim(qp.get("month"));
                break;
            }
        }
        log.debug("## onlineSubjectId:[{}], month:[{}]", onlineSubjectId, month);

        // TODO: 아래의 코드를 주석해제하면 오프라인 진도가 적용됨
        LocalDate localDate = LocalDate.now();
        int monthValue = localDate.getMonthValue();
        if(10 > monthValue) {
            month = "0" + monthValue;
        } else {
            month = "" + monthValue;
        }

        UserVO userVO = cacheService.getTokenUser();
        if(userVO.getUsername().equals(TokenProvider.TRIAL_USER)) {
            // 체험회원 룰 적용
            onlineSubjectId = 9;
            month = "01";
        }
        else if(userVO.getUsername().startsWith("test01")) {
            onlineSubjectId = 2;
        } else if(userVO.getUsername().startsWith("test02")) {
            onlineSubjectId = 3;
        } else if(userVO.getUsername().startsWith("test03")) {
            onlineSubjectId = 4;
        } else {
            onlineSubjectId = 1;
        }
        reqVo.setOnlineSubjectId(onlineSubjectId);
        reqVo.setMonth(month);
        //------------------- 오프라인 진도를 반영한 온라인과목/월 조회 - end ---------------------

        resVo.setList(scheduleDAO.selectThisMonthBookList(reqVo));

        return resVo;
    }

    /**
     * 이달의 도서 팝업
     *
     * @param reqVo
     * @param resVo
     * @return
     */
    public ResSchedulePopupVO selectPopup(ReqSchedulePopupVO reqVo, ResSchedulePopupVO resVo) {

        ResScheduleVO item = scheduleDAO.selectPopup(reqVo);

        if(item == null){ SystemUtil.returnNoSearchResult(); } // 조회 결과 없음 리턴

        resVo.setItem(item);

        return resVo;
    }
}
