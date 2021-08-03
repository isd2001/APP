package codegurus.learning;

import codegurus.cmm.cache.CacheService;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import codegurus.learning.vo.*;
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
public class LearningController extends BaseController {

    @Autowired
    private LearningService learningService;

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
    public Res<ResLearningBookVO> book(@RequestBody @Valid ReqLearningBookVO reqVo) {

        ResLearningBookVO resVo = new ResLearningBookVO();
        learningService.selectBookDetail(reqVo, resVo);
        return new Res<ResLearningBookVO>(resVo);
    }

    /**
     * 학습 콘텐츠
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/contents")
    @ApiOperation(value = "학습 콘텐츠")
    public Res<ResStudyContentsListVO> contents(@RequestBody @Valid ReqStudyContentsListVO reqVo) {

        ResStudyContentsListVO resVo = learningService.selectStudyContents(reqVo);
        return new Res<ResStudyContentsListVO>(resVo);
    }

    /**
     * 학습 콘텐츠 이력 저장
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "학습 콘텐츠 이력 저장")
    public Res<ResLearningContentsHistorySaveVO> save(@RequestBody @Valid ReqLearningContentsHistorySaveVO reqVo) {

        ResLearningContentsHistorySaveVO resVo= new ResLearningContentsHistorySaveVO();
        reqVo.setRegId(cacheService.getUserManageId());
        learningService.saveLearningContentsHistory(reqVo, resVo);

        return new Res<ResLearningContentsHistorySaveVO>(resVo);

    }
}
