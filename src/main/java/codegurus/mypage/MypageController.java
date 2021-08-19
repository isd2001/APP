package codegurus.mypage;

import codegurus.auth.vo.ReqDeleteUserVO;
import codegurus.auth.vo.ReqUpdatePWVO;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.req.ReqBaseVO;
import codegurus.cmm.vo.res.Res;
import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.mypage.vo.*;
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
        reqVo.setUserManageId(cacheService.getUserManageId());
        ResMagnitudeListVO resVo = mypageService.selectmagnitudeList(reqVo);

        return new Res<ResMagnitudeListVO>(resVo);
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
    @PostMapping("dicList")
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
    @PostMapping("portfolioList")
    @ApiOperation(value = "나의 포트폴리오 목록 조회")
    public Res<ResPortfolioListVO> portfolioList(@RequestBody @Valid ReqPortfolioListVO reqVo) {
        reqVo.setUserManageId(cacheService.getUserManageId());
        ResPortfolioListVO resVo = mypageService.selectportfolioList(reqVo);

        return new Res<ResPortfolioListVO>(resVo);
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

        reqVo.setUserManageId(cacheService.getUserManageId());
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

}
