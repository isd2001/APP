package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.req.*;
import codegurus.cmm.vo.res.*;
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
 * 인증 처리 관련 controller
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@Api(tags = "인증 관련 컨트롤러")
@RequestMapping("/auth")
@RestController
public class AuthController extends BaseController {

	@Autowired
	private AuthService authService;

	/**
	 * 로그인 처리
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "로그인")
	@PostMapping("/login")
	public Res<ResAuthVO> login(@RequestBody @Valid ReqAuthVO reqVo) {

		ResAuthVO resVo = authService.login(reqVo);
		return new Res<ResAuthVO>(resVo);
	}

	/**
	 * 사용자 중복 확인 (회원가입 화면)
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "사용자 중복 확인 (회원가입 화면)")
	@PostMapping("/checkUserDup")
	public Res<ResBaseVO> checkUserDup(@RequestBody @Valid ReqDupCheckVO reqVo) {

		ResBaseVO resVo = authService.selectUserDup(reqVo);
		return new Res<ResBaseVO>(resVo);
	}

	/**
	 * 회원가입
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "회원가입 (작업중)")
	@PostMapping("/register")
	public Res<ResRegisterVO> register(@RequestBody @Valid ReqRegisterVO reqVo) {

		ResRegisterVO resVo = authService.register(reqVo);
		return new Res<ResRegisterVO>(resVo);
	}

	/**
	 * 체험회원 등록
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "체험회원 등록")
	@PostMapping("/trialRegister")
	public Res<ResTrialRegisterVO> trialRegister(@RequestBody @Valid ReqTrialRegisterVO reqVo) {

		ResTrialRegisterVO resVo = authService.trialRegister(reqVo);
		return new Res<ResTrialRegisterVO>(resVo);
	}

	/**
	 * 정회원 인증
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "정회원 인증 (작업중)")
	@PostMapping("/fullmemberAuth")
	public Res<ResFullMemberAuthVO> fullmemberAuth(@RequestBody @Valid ReqFullMemberAuthVO reqVo) {

		ResFullMemberAuthVO resVo = authService.fullmemberAuth(reqVo);
		return new Res<ResFullMemberAuthVO>(resVo);
	}

	/**
	 * VOC 호출
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "VOC 호출 (작업중)")
	@PostMapping("/callVoc")
	public Res<ResVocVO> callVoc(@RequestBody @Valid ReqVocVO reqVo) {

		ResVocVO resVo = authService.callVoc(reqVo);
		return new Res<ResVocVO>(resVo);
	}

}