package codegurus.auth;

import codegurus.auth.vo.ResUserVO;
import codegurus.auth.vo.UserVO;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.req.ReqAuthVO;
import codegurus.cmm.vo.res.Res;
import codegurus.cmm.vo.res.ResAuthVO;
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
	 * 토큰 정보 확인
	 *
	 * @return
	 */
	@ApiOperation(value = "토근 정보 확인")
	@PostMapping("/info")
	public Res<ResUserVO> info() {

		ResUserVO resVo = new ResUserVO();
		resVo.setUserId("testuser");
		resVo.setName("김개발");

		return new Res<ResUserVO>(resVo);
	}

}