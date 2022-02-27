package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.constants.ResCodeEnum;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.exception.CustomException;
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
@Api(tags = "인증")
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
	@ApiOperation(value = "회원가입 > 사용자 중복 확인")
	@PostMapping("/checkUserDup")
	public Res<ResBaseVO> checkUserDup(@RequestBody @Valid ReqDupCheckVO reqVo) {

		ResBaseVO resVo = authService.selectUserDup(reqVo);
		return new Res<ResBaseVO>(resVo);
	}

	/**
	 * 회원가입 (학생)
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "회원가입 (학생)")
	@PostMapping("/register")
	public Res<ResRegisterVO> register(@RequestBody @Valid ReqRegisterVO reqVo) {

		// 약관동의 여부 확인
		if(! "Y".equals(reqVo.getTermofuseAgreeOrnot())){
			throw new CustomException(ResCodeEnum.INFO_0004.name(), "서비스 이용 약관 동의가 필요합니다.");
		}
		if(! "Y".equals(reqVo.getPersonalinfoAgreeOrnot())){
			throw new CustomException(ResCodeEnum.INFO_0004.name(), "개인정보 수집 및 이용에 대한 동의가 필요합니다.");
		}

		ResRegisterVO resVo = authService.register(reqVo);
		return new Res<ResRegisterVO>(resVo);
	}

	/**
	 * 회원가입 (학부모)
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "회원가입 (학부모) [프렌즈몬 전용]")
	@PostMapping("/registerParent")
	public Res<ResRegisterVO> registerParent(@RequestBody @Valid ReqRegisterParentVO reqVo) {

		ResRegisterVO resVo = authService.registerParent(reqVo);
		return new Res<ResRegisterVO>(resVo);
	}

	/**
	 * 자녀 연결
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "자녀 연결 (학부모 회원이 기 등록 자녀회원과의 가족관계 연결을 설정하는 작업) [프렌즈몬 전용]")
	@PostMapping("/connectChild")
	public Res<ResConnectChildVO> connectChild(@RequestBody @Valid ReqConnectChildVO reqVo) {

		ResConnectChildVO resVo = authService.connectChild(reqVo);
		return new Res<ResConnectChildVO>(resVo);
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
	 * 상담 신청
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "상담 신청")
	@PostMapping("/counselReq")
	public Res<ResCounselVO> counselReq(@RequestBody @Valid ReqCounselVO reqVo) {

		ResCounselVO resVo = authService.reqCounsel(reqVo);
		return new Res<ResCounselVO>(resVo);
	}

	/**
	 * 계약정보 조회 (정회원 인증 전)
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "계약정보 조회 (정회원 인증 직전)")
	@PostMapping("/contractInfo")
	public Res<ResContractInfoVO> contractInfo(@RequestBody @Valid ReqContractInfoVO reqVo) {

		ResContractInfoVO resVo = authService.getContractInfo(reqVo);
		return new Res<ResContractInfoVO>(resVo);
	}

	/**
	 * 정회원 인증
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "정회원 인증")
	@PostMapping("/fullmemberAuth")
	public Res<ResFullmemberAuthVO> fullmemberAuth(@RequestBody @Valid ReqFullmemberAuthVO reqVo) {

		ResFullmemberAuthVO resVo = authService.fullmemberAuth(reqVo);
		return new Res<ResFullmemberAuthVO>(resVo);
	}

	/**
	 * SMS 인증 요청
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "SMS 인증 요청 (SMS발송모듈 연동 제외하고 완료. 업무 흐름은 가능)")
	@PostMapping("/smsCertReq")
	public Res<ResSmsCertVO> smsCertReq(@RequestBody @Valid ReqSmsCertVO reqVo) {

		ResSmsCertVO resVo = authService.reqSmsCert(reqVo);
		return new Res<ResSmsCertVO>(resVo);
	}

	/**
	 * SMS 인증번호 확인
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "SMS 인증번호 확인 (SMS발송모듈 연동 제외하고 완료. 업무 흐름은 가능)")
	@PostMapping("/smsCertCfm")
	public Res<ResSmsCertCfmVO> smsCertCfm(@RequestBody @Valid ReqSmsCertCfmVO reqVo) {

		ResSmsCertCfmVO resVo = authService.cfmSmsCert(reqVo);
		return new Res<ResSmsCertCfmVO>(resVo);
	}

	/**
	 * ID 찾기
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "ID 찾기")
	@PostMapping("/findId")
	public Res<ResFindIDVO> findId(@RequestBody @Valid ReqFindIDVO reqVo) {

		ResFindIDVO resVo = authService.findId(reqVo);
		return new Res<ResFindIDVO>(resVo);
	}

	/**
	 * 패스워드 찾기
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "패스워드 찾기")
	@PostMapping("/findPw")
	public Res<ResFindPWVO> findPw(@RequestBody @Valid ReqFindPWVO reqVo) {

		ResFindPWVO resVo = authService.findPw(reqVo);
		return new Res<ResFindPWVO>(resVo);
	}

	/**
	 * 패스워드 찾기 후 패스워드 변경
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "패스워드 찾기 후 변경")
	@PostMapping("/findPwUpdate")
	public Res<ResBaseVO> findPwUpdate(@RequestBody @Valid ReqFindPWUpdateVO reqVo) {

		ResBaseVO resVo = authService.findPwUpdate(reqVo);
		return new Res<ResBaseVO>(resVo);
	}

	/**
	 * 사용 가능 상품(스마트독서, 플라톤..) 목록 출력
	 *
	 * @param reqVo
	 * @return
	 */
	@ApiOperation(value = "사용 가능 상품(스마트독서, 플라톤..) 목록 출력")
	@PostMapping("/availProds")
	public Res<ResAvailProdsVO> availProds(@RequestBody @Valid ReqBaseVO reqVo) {

		ResAvailProdsVO resVo = authService.getAvailProds(reqVo);
		return new Res<ResAvailProdsVO>(resVo);
	}

}