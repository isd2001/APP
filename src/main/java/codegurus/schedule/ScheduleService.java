package codegurus.schedule;

import codegurus.auth.AuthDAO;
import codegurus.auth.vo.ScheduleInfoVO;
import codegurus.auth.vo.UserVO;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.constants.EduStatCdEnum;
import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.SystemUtil;
import codegurus.schedule.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

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

        // 기존에는 DW정보로 진도를 가져왔는데 자체 관리자에서 진도조정으로 변경되서 필요 없어짐
        /* List<String> eduCntrIdList = authDAO.selectUserSubjectList(reqVo.getUserManageId());
        log.debug("## eduCntrIdList:{}", JsonUtil.toJson(eduCntrIdList));
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
        log.debug("## onlineSubjectId:[{}], month:[{}]", onlineSubjectId, month);*/

        LocalDate localDate = LocalDate.now();
        int monthValue = localDate.getMonthValue();
        String nowMonth = null;
        if(10 > monthValue) {
            nowMonth = "0" + monthValue;
        } else {
            nowMonth = "" + monthValue;
        }

        UserVO userVO = cacheService.getTokenUser(reqVo.getProductId());
        log.debug("## getUsername:", userVO.getUsername());
        if(userVO.getUsername().equals(TokenProvider.TRIAL_USER)) {
            // 체험회원 룰 적용
            int yearValue = localDate.getYear(); // 현재 년도
            int birthYear = Integer.parseInt(userVO.getBirthYear());

            int trialUserAge = (yearValue-birthYear) + 1;

            if(reqVo.getProductId().equals(ProductEnum.상품_스마트독서.getProductId())) {
                if(trialUserAge <= 7) {
                    onlineSubjectId = 9;
                } else if(trialUserAge == 8) {
                    onlineSubjectId = 10;
                } else if(trialUserAge == 9) {
                    onlineSubjectId = 11;
                } else if(trialUserAge >= 10) {
                    onlineSubjectId = 12;
                }
            } else if(reqVo.getProductId().equals(ProductEnum.상품_플라톤.getProductId())) {
                if(trialUserAge <= 8) {
                    onlineSubjectId = 13;
                } else if(trialUserAge == 9) {
                    onlineSubjectId = 14;
                } else if(trialUserAge == 10) {
                    onlineSubjectId = 15;
                } else if(trialUserAge >= 11) {
                    onlineSubjectId = 16;
                }
            }

            month = nowMonth;
        }
        // 실제 정회원, 수업중이 하나라도 있으면 해당
        // TODO 플라톤 스케줄 관련해서 수정해야함
        else {
            ScheduleInfoVO scheduleInfoVO = new ScheduleInfoVO();
            scheduleInfoVO.setProductId(reqVo.getProductId());
            scheduleInfoVO.setUserManageId(reqVo.getUserManageId());
            List<ScheduleInfoVO> scheduleInfoList = authDAO.selectScheduleInfo(scheduleInfoVO);

            // 스케줄을 불러와야할때만 true
            boolean isSet = false;

            String birth = null;
            int scheduleIntervalValue = 0;
            String sapSubjId = null;

            for(ScheduleInfoVO info : scheduleInfoList) {
                // DW에 정보가 없어도 강제 인증한것으로 인식
                if(info.getUserStatusCode() != null && info.getUserStatusCode().equals("01")) {
                    isSet = true;
                    birth = info.getBirth();
                    scheduleIntervalValue = info.getValue();
                    sapSubjId = info.getSapSubjId();
                }
                // 하나라도 수업중이면 인증한것으로 인식
                else if(info.getEduStatCd() != null && info.getEduStatCd().equals(EduStatCdEnum.수업중.getCode())) {
                    isSet = true;
                    birth = info.getBirth();
                    scheduleIntervalValue = info.getValue();
                    sapSubjId = info.getSapSubjId();
                }
            }

            // 스케줄 정보 가져오기
            if(isSet) {
                // 값 비교를 위해 현재 나이x12+현재월을 계산한다. 플라톤는 학년과 상관없어서 나이를 0으로 변환
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
                int currentAge = reqVo.getProductId().equals(ProductEnum.상품_플라톤.getProductId()) ? 0 : Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(birth.substring(0, 4)) + 1;

                int totalMonth = currentAge * 12 + currentMonth + scheduleIntervalValue;

                // 범위가 벗어나면 가장 가까운 값을 보내주는것으로 한다. 예외에 대해 안정해줌
                // 상품별로 구간이 다르다 스마트독서 7살1월 ~ 10살12월, 플라톤 1월 ~ 12월
                int minMonth = 0;
                int maxMonth = 0;
                if(reqVo.getProductId().equals(ProductEnum.상품_스마트독서.getProductId())) {
                    minMonth = 7 * 12 + 1;
                    maxMonth = 10 * 12 + 12;

                    if(totalMonth < minMonth) {
                        totalMonth = minMonth;
                    } else if(maxMonth < totalMonth) {
                        totalMonth = maxMonth;
                    }
                } else if(reqVo.getProductId().equals(ProductEnum.상품_플라톤.getProductId())) {
                    if(totalMonth > 12) {
                        totalMonth -= 12;
                    }
                }

                monthValue = (totalMonth % 12 == 0 ? 12 : totalMonth % 12);
                if(10 > monthValue) {
                    month = "0" + monthValue;
                } else {
                    month = "" + monthValue;
                }

                if(reqVo.getProductId().equals(ProductEnum.상품_스마트독서.getProductId())) {

                    if(month.equals("12")) {
                        totalMonth -= 12;
                    }

                    switch (totalMonth / 12) {
                        case 7:
                            onlineSubjectId = 1;
                            break;
                        case 8:
                            onlineSubjectId = 2;
                            break;
                        case 9:
                            onlineSubjectId = 3;
                            break;
                        case 10:
                            onlineSubjectId = 4;
                            break;
                    }
                } else if(reqVo.getProductId().equals(ProductEnum.상품_플라톤.getProductId())) {
                    // 플라톤은 상품코드에 따라 다르다.
                    switch (sapSubjId) {
                        case "802450":
                        case "802459":
                        case "802468":
                            onlineSubjectId = 5;
                            break;
                        case "802451":
                        case "802460":
                        case "802469":
                            onlineSubjectId = 6;
                            break;
                        case "802452":
                        case "802461":
                        case "802470":
                            onlineSubjectId = 7;
                            break;
                        case "802453":
                        case "802462":
                        case "802471":
                            onlineSubjectId = 8;
                            break;
                    }
                }
            }
        }

        log.debug("##1234 onlineSubjectId:[{}], month:[{}]", onlineSubjectId, month);
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
