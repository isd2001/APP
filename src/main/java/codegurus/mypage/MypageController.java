package codegurus.mypage;

import codegurus.cmm.cache.CacheService;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import codegurus.mypage.vo.ReqBookcaseListVO;
import codegurus.mypage.vo.ReqMagnitudeListVO;
import codegurus.mypage.vo.ResBookcaseListVO;
import codegurus.mypage.vo.ResMagnitudeListVO;
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
 * 마이페이지 controller
 *
 * @author 이미란
 * @version 2021.08
 */
@Slf4j
@Api( tags = "마이페이지")
@RequestMapping("/mypage")
@RestController
public class MypageController extends BaseController {

    @Autowired
    private MypageService mypageService;

    @Autowired
    private CacheService cacheService;

    /**
     * 나의 진도
     *
     * @param reqVo
     * @return
     */
    @PostMapping("magnitudeList")
    @ApiOperation(value = "나의 진도 목록 조회")
    public Res<ResMagnitudeListVO> magnitudeList(@RequestBody @Valid ReqMagnitudeListVO reqVo) {

        ResMagnitudeListVO resVo = mypageService.selectmagnitudeList(reqVo);
        reqVo.setUserManageId(cacheService.getUserManageId());
        return new Res<>(resVo);
    }

    /**
     * 나의 책장
     *
     * @param reqVo
     * @return
     */
    @PostMapping("bookcaseList")
    @ApiOperation(value = "나의 책장 목록 조회")
    public Res<ResBookcaseListVO> bookcaseList(@RequestBody @Valid ReqBookcaseListVO reqVo) {

        ResBookcaseListVO resVo = mypageService.selectBookcaseList(reqVo);
        reqVo.setUserManageId(cacheService.getUserManageId());
        return new Res<ResBookcaseListVO>(resVo);
    }
}
