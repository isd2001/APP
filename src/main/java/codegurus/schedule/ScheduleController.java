package codegurus.schedule;

import codegurus.cmm.cache.CacheService;
import codegurus.schedule.vo.*;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 도서 일정 controller
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Api (tags = "스케줄")
@RequestMapping("/schedule")
@RestController
public class ScheduleController extends BaseController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CacheService cacheService;

    /**
     * 온라인 과목 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/subjectList")
    @ApiOperation(value = "온라인 과목 목록 조회(도서 일정 좌측)")
    public Res<ResSubjectListVO> subjectList(@RequestBody @Valid ReqSubjectListVO reqVo) {
        //reqVo.setServiceCode("PRC");
        ResSubjectListVO resVo = scheduleService.selectSubjectList(reqVo);
        return new Res<ResSubjectListVO>(resVo);
    }

    /**
     * 도서 일정 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/bookScheduleList")
    @ApiOperation(value = "도서 일정 목록 조회(도서 일정 우측)")
    public Res<ResScheduleListVO> bookScheduleList(@RequestBody @Valid ReqScheduleListVO reqVo) {

        // 일단 연도를 안쓰고 있어서 rcms에서 넣어주는 9999로 세팅
        reqVo.setYear("9999");

        ResScheduleListVO resVo = scheduleService.selectBookScheduleList(reqVo);
        return new Res<ResScheduleListVO>(resVo);
    }

    /**
     * 이달의 도서 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/thisMonthBookList")
    @ApiOperation(value = "이달의 도서 조회")
    public Res<ResScheduleListVO> selectThisMonthBookList(@RequestBody @Valid ReqThisMonthBookVO reqVo) {
        reqVo.setUserManageId(cacheService.getUserManageId());
        reqVo.setMonth("08");
        reqVo.setOnlineSubjectId(1);
        ResScheduleListVO resVo = scheduleService.selectThisMonthBookList(reqVo);
        return new Res<ResScheduleListVO>(resVo);
    }

    /**
     * 이달의 도서 팝업
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/popup")
    @ApiOperation(value = "이달의 도서 팝업 -> 이달의 도서 페이지에서 오브제 선택시 팝업")
    public Res<ResSchedulePopupVO> selectPopup(@RequestBody @Valid ReqSchedulePopupVO reqVo) {
        ResSchedulePopupVO resVo = new ResSchedulePopupVO();
        scheduleService.selectPopup(reqVo, resVo);
        return new Res<ResSchedulePopupVO>(resVo);
    }
}
