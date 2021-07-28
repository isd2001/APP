package codegurus.schedule;

import codegurus.schedule.vo.ReqScheduleListVO;
import codegurus.schedule.vo.ReqSubjectListVO;
import codegurus.schedule.vo.ResScheduleListVO;
import codegurus.schedule.vo.ResSubjectListVO;
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
@Api (tags = "도서 일정")
@RequestMapping("/schedule")
@RestController
public class ScheduleController extends BaseController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 온라인 과목 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/subjectList")
    @ApiOperation(value = "온라인 과목 목록 조회")
    public Res<ResSubjectListVO> subjectList(@RequestBody @Valid ReqSubjectListVO reqVo) {

        ResSubjectListVO resVo = scheduleService.selectSubjectList(reqVo);
        return new Res<ResSubjectListVO>(resVo);
    }

    /**
     * 도서 일정 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "도서 일정 목록 조회")
    public Res<ResScheduleListVO> scheduleList(@RequestBody @Valid ReqScheduleListVO reqVo) {

        ResScheduleListVO resVo = scheduleService.selectScheduleList(reqVo);
        return new Res<ResScheduleListVO>(resVo);
    }
}
