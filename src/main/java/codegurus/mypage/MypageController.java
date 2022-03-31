package codegurus.mypage;

import codegurus.auth.vo.ReqDeleteUserVO;
import codegurus.auth.vo.ReqUpdatePWVO;
import codegurus.board.BoardService;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.LearningService;
import codegurus.learning.vo.ReqLearningResultVO;
import codegurus.learning.vo.ResLearningResultVO;
import codegurus.mypage.vo.*;
import codegurus.oneline.OnelineService;
import codegurus.oneline.vo.ReqOnelineSaveVO;
import codegurus.oneline.vo.ReqOnelineUpdateVO;
import codegurus.oneline.vo.ResOnelineSaveVO;
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
    private OnelineService onelineService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private LearningService learningService;


    /**
     * 나의 진도
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/magnitudeList")
    @ApiOperation(value = "나의 진도 목록 조회")
    public Res<ResMagnitudeListVO> magnitudeList(@RequestBody @Valid ReqMagnitudeListVO reqVo) {
        reqVo.setUserManageId(cacheService.getUserManageId());
        ResMagnitudeListVO resVo = mypageService.selectmagnitudeList(reqVo);

        return new Res<ResMagnitudeListVO>(resVo);
    }

    /**
     * 나의 진도 > 학습 결과
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/learningResult")
    @ApiOperation(value = "나의 진도 > 학습 결과 조회")
    public Res<ResLearningResultVO> learningResult(@RequestBody @Valid ReqLearningResultVO reqVo) {

        ResLearningResultVO resVo = new ResLearningResultVO();

        reqVo.setUserManageId(cacheService.getUserManageId());  // 학습 결과 조회 대상 회원 관리ID
        reqVo.setOnlineSubjectScheduleId(reqVo.getOnlineSubjectScheduleId());

        learningService.selectLearningResult(reqVo, resVo);
        return new Res<ResLearningResultVO>(resVo);
    }

    /**
     * 나의 책장
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/bookcaseList")
    @ApiOperation(value = "나의 책장 목록 조회")
    public Res<ResBookcaseListVO> bookcaseList(@RequestBody @Valid ReqBookcaseListVO reqVo) {
        reqVo.setUserManageId(cacheService.getUserManageId());
        ResBookcaseListVO resVo = mypageService.selectBookcaseList(reqVo);

        return new Res<ResBookcaseListVO>(resVo);
    }

    /**
     * 나의 사전
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/dicList")
    @ApiOperation(value = "나의 사전 목록 조회")
    public Res<ResDicListVO> dicList(@RequestBody @Valid ReqDicListVO reqVo) {
        reqVo.setUserManageId(cacheService.getUserManageId());
        ResDicListVO resVo = mypageService.selectDicList(reqVo);

        return new Res<ResDicListVO>(resVo);
    }

    /**
     * 나의 포트 폴리오
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/portfolioList")
    @ApiOperation(value = "나의 포트폴리오 목록 조회")
    public Res<ResPortfolioListVO> portfolioList(@RequestBody @Valid ReqPortfolioListVO reqVo) {
        reqVo.setUserManageId(cacheService.getUserManageId());
        ResPortfolioListVO resVo = mypageService.selectportfolioList(reqVo);

        return new Res<ResPortfolioListVO>(resVo);
    }

    /**
     * 한줄평, 별점 수정
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/updateOneline")
    @ApiOperation(value = "한줄평 등록/수정")
    public Res<ResOnelineSaveVO> updateOneline(@RequestBody @Valid ReqOnelineUpdateVO reqVo) {
        ResOnelineSaveVO resVo = new ResOnelineSaveVO();

        ReqOnelineSaveVO vo = new ReqOnelineSaveVO();

        vo.setRegId(cacheService.getUserManageId());
        vo.setModifyId(cacheService.getUserManageId());
        vo.setOnelinereviewId(reqVo.getOnelinereviewId());
        vo.setBookId(reqVo.getBookId());
        vo.setOnelinereviewContent(reqVo.getOnelinereviewContent());
        vo.setScore(reqVo.getScore());

        onelineService.updateOneline(vo, resVo);

        return new Res<ResOnelineSaveVO>(resVo);
    }

    /**
     * 회원정보 > 회원정보 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("userInfo")
    @ApiOperation(value = "회원정보 > 회원정보 조회")
    public Res<ResUserInfoVO> userInfo(@RequestBody @Valid ReqUserInfoVO reqVo) {

        ResUserInfoVO resVo = mypageService.selectUserInfo(reqVo);

        return new Res<ResUserInfoVO>(resVo);
    }

    /**
     * 회원정보 > 회원정보 수정
     *
     * @param reqVo
     * @return
     */
    @PostMapping("userUpdate")
    @ApiOperation(value = "회원정보 > 회원정보 수정")
    public Res<ResBaseVO> userUpdate(@RequestBody @Valid ReqUserUpdateVO reqVo) {

        reqVo.setUserManageId(cacheService.getUserManageId());
        ResBaseVO resVo = mypageService.updateUserInfo(reqVo);

        return new Res<ResBaseVO>(resVo);
    }

	/**
	 * 회원정보 > 패스워드 변경
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "회원정보 > 패스워드 변경")
	@PostMapping("/updatePW")
	public Res<ResBaseVO> updatePW(@RequestBody @Valid ReqUpdatePWVO reqVo) {

	    reqVo.setUserManageId(cacheService.getUserManageId());
		ResBaseVO resVo = mypageService.updatePW(reqVo);
		return new Res<ResBaseVO>(resVo);
	}

	/**
	 * 회원정보 > 계정 삭제 > 패스워드 확인
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "회원정보 > 계정 삭제 > 패스워드 확인 (중요한 작업 전 본인의 패스워드 한 번 더 확인)")
	@PostMapping("/makeSurePW")
	public Res<ResBaseVO> makeSurePW(@RequestBody @Valid ReqUpdatePWVO reqVo) {

	    reqVo.setUserManageId(cacheService.getUserManageId());
		ResBaseVO resVo = mypageService.makeSurePW(reqVo);
		return new Res<ResBaseVO>(resVo);
	}

	/**
	 * 회원정보 > 계정 삭제
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "회원정보 > 계정 삭제")
	@PostMapping("/deleteUser")
	public Res<ResBaseVO> deleteUser(@RequestBody @Valid ReqDeleteUserVO reqVo) {

        reqVo.setUserManageId(cacheService.getUserManageId());
		ResBaseVO resVo = mypageService.deleteUser(reqVo);
		return new Res<ResBaseVO>(resVo);
	}

    /**
     * 마케팅 활용 동의
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/promotionAgree")
    @ApiOperation(value = "마케팅 활용 동의")
    public Res<ResBaseVO> promotionAgree (@RequestBody @Valid ReqPromotionAgreeVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        reqVo.setUserManageId(cacheService.getUserManageId());
        mypageService.promotionAgree(reqVo, resVo);

        return new Res<ResBaseVO>(resVo);
    }

    /**
     * 앱 푸시 동의
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/appPushAgree")
    @ApiOperation(value = "앱 푸시 동의")
    public Res<ResBaseVO> appPushAgree(@RequestBody @Valid ReqAppPushAgreeVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        reqVo.setUserManageId(cacheService.getUserManageId());
        mypageService.appPushAgree(reqVo, resVo);

        return new Res<ResBaseVO>(resVo);
    }

    /**
     * 음원 재생 수정
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/soundtrackPlayUpdate")
    @ApiOperation(value = "음원 재생 수정")
    public Res<ResBaseVO> soundtrackPlayUpdate(@RequestBody @Valid ReqSoundtrackPlayUpdateVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        reqVo.setUserManageId(cacheService.getUserManageId());
        mypageService.soundtrackPlayUpdate(reqVo, resVo);

        return new Res<ResBaseVO>(resVo);
    }

    /**
     * 가이드 활성화 수정
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/guideActivateUpdate")
    @ApiOperation(value = "가이드 활성화 수정")
    public Res<ResBaseVO> guideActivateUpdate(@RequestBody @Valid ReqGuideActivateUpdateVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        reqVo.setUserManageId(cacheService.getUserManageId());
        mypageService.guideActivateUpdate(reqVo, resVo);

        return new Res<ResBaseVO>(resVo);
    }

    /**
     * 테마 모드 수정
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/themeModeUpdate")
    @ApiOperation(value = "테마 모드 수정")
    public Res<ResBaseVO> themeModeUpdate(@RequestBody @Valid ReqThemeModeUpdateVO reqVo) {

        ResBaseVO resVo = new ResBaseVO();

        reqVo.setUserManageId(cacheService.getUserManageId());
        mypageService.themeModeUpdate(reqVo, resVo);

        return new Res<ResBaseVO>(resVo);
    }
}
