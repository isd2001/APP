package codegurus.oneline;

import codegurus.cmm.cache.CacheService;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import codegurus.oneline.vo.*;
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
 * 한줄평/좋아요/별점 controller
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Api( tags = "한줄평/좋아요/별점")
@RequestMapping("/oneline")
@RestController
public class OnelineController extends BaseController {

    @Autowired
    private OnelineService onelineService;

    @Autowired
    private CacheService cacheService;

    /**
     * 오늘의 학습 책 한줄평 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "오늘의 학습 책 한줄평 목록 조회")
    public Res<ResOnelineListVO> list(@RequestBody @Valid ReqOnelineListVO reqVo) {
        ResOnelineListVO resVo = onelineService.selectOnelineList(reqVo);
        return new Res<ResOnelineListVO>(resVo);
    }

    /**
     * 오늘의 학습 책 한줄평 등록
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/content")
    @ApiOperation(value = "오늘의 학습 책 한줄평 내용 등록")
    public Res<ResOnelineSaveVO> saveOnline(@RequestBody @Valid ReqOnelineContentSaveVO reqVo) {
        ResOnelineSaveVO resVo = new ResOnelineSaveVO();

        ReqOnelineSaveVO vo = new ReqOnelineSaveVO();
        vo.setRegId(cacheService.getUserManageId());
        vo.setSaveType("C");
        vo.setBookId(reqVo.getBookId());
        vo.setOnelinereviewContent(reqVo.getOnelinereviewContent());

        onelineService.saveOneline(vo, resVo);

        return new Res<ResOnelineSaveVO>(resVo);
    }

    /**
     * 오늘의 학습 책 별점 등록
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/score")
    @ApiOperation(value = "오늘의 학습 책 별점 등록")
    public Res<ResOnelineSaveVO> saveScore(@RequestBody @Valid ReqOnelineScoreVO reqVo) {
        ResOnelineSaveVO resVo = new ResOnelineSaveVO();

        ReqOnelineSaveVO vo = new ReqOnelineSaveVO();
        vo.setRegId(cacheService.getUserManageId());
        vo.setSaveType("S");
        vo.setBookId(reqVo.getBookId());
        vo.setScore(reqVo.getScore());

        onelineService.saveOneline(vo, resVo);

        return new Res<ResOnelineSaveVO>(resVo);
    }

    /**
     * 한줄평 좋아요 추가/제거
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/markGood")
    @ApiOperation(value = "오늘의 학습 책 한줄평 좋아요 추가/제거")
    public Res<ResOnelineMarkGoodVO> markGood(@RequestBody @Valid ReqOnelineMarkGoodVO reqVo) {
        ResOnelineMarkGoodVO resVo = new ResOnelineMarkGoodVO();
        reqVo.setRegId(cacheService.getUserManageId());
        onelineService.saveMarkGood(reqVo, resVo);
        return new Res<ResOnelineMarkGoodVO>(resVo);
    }

    /**
     * 내가 쓴 한줄평 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/myList")
    @ApiOperation(value = "내가 쓴 한줄평")
    public Res<ResOnelineListVO> myList(@RequestBody @Valid ReqOnelineListVO reqVo) {
        reqVo.setRegId(cacheService.getUserManageId());
        ResOnelineListVO resVo = onelineService.selectOnelineList(reqVo);

        return new Res<ResOnelineListVO>(resVo);
    }
}
