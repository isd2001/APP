package codegurus.study;

import codegurus.cmm.cache.CacheService;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import codegurus.study.vo.*;
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
 * 학습 controller
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Api( tags = "학습")
@RequestMapping("/study")
@RestController
public class StudyController extends BaseController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private CacheService cacheService;

    /**
     * 오늘의 학습 책 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/book")
    @ApiOperation(value = "오늘의 학습 책 조회")
    public Res<ResStudyBookVO> book(@RequestBody @Valid ReqStudyBookVO reqVo) {

        ResStudyBookVO resVo = studyService.selectBookDetail(reqVo);
        return new Res<ResStudyBookVO>(resVo);
    }

    /**
     * 학습 콘텐츠
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/contents")
    @ApiOperation(value = "학습 콘텐츠")
    public Res<ResStudyContentsListVO> voca(@RequestBody @Valid ReqStudyContentsListVO reqVo) {

        ResStudyContentsListVO resVo = studyService.selectStudyContentsList(reqVo);
        return new Res<ResStudyContentsListVO>(resVo);
    }

}
