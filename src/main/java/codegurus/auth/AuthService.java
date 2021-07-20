package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.constants.Constants;
import codegurus.cmm.constants.ResCodeEnum;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.WebUtil;
import codegurus.cmm.vo.req.*;
import codegurus.cmm.vo.res.*;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import codegurus_ext.voc.VocDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 인증 처리 관련 service
 *
 *  - 일반적인 서비스와 달리 spring security 처리를 위해 UserDetailsService 구현 (JWT 구성요소에 포함)
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

    /**
     * 거의 모든 예제에서  authenticationManagerBuilder.getObject().authenticate() 를 사용하여 인증처리를 하는 것으로 나와 있어서 상당히 많은 시간을 들여 테스트 했으나,
     * 결국 authenticationManagerBuilder.getObject() 에서 발생하는 'IllegalStateException: This object has not been built' 를 해결하지 못했다.
     * 다행히 다른 글을 보던 중 SecurityConfig.authenticationManagerBean() override 를 통해 authenticationManager를 bean 화 하는 것이 있어서 혹시나 하고 해 보았더니 작동..ㅠㅠ..
     * spring security 의 기준을 충족시키기 위해 너무 많은 시간을 투자했는데, 과연 의미가 있는지 되짚어 봐야 겠다.
     */
//	@Autowired
//	private AuthenticationManagerBuilder authenticationManagerBuilder;
	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private AuthDAO authDAO;

    @Autowired
    private VocDAO vocDAO;

    @Autowired
    private HttpServletRequest httpRequest;

    /**
     * 로그인
     *
     *  - 성공하면 access token 발급
     *
     * @param reqVo
     * @return
     */
    public ResAuthVO login(ReqAuthVO reqVo){

        log.debug("## reqVo:[{}]", reqVo);

		String paramPWEnc = passwordEncoder.encode(StringUtil.trim(reqVo.getPassword()));
		log.debug("## paramPWEnc:[{}]", paramPWEnc);

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqVo.getUsername(), reqVo.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); // 죽어라 안된다.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        ResAuthVO resVo = tokenProvider.generateTokenDto(authentication);

//         4. RefreshToken 저장 (현재(2021.06) 정책상 사용하지 않음)
//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authentication.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();
//        refreshTokenRepository.save(refreshToken);

//         5. 토큰 발급
        return resVo;

    }

    /**
     * 사용자ID로 사용자 정보 조회
     *
     *  - UserDetailsService interface 메서드
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserVO vo = commonDAO.selectUserByUserId(username);
        if (vo == null) {
            WebUtil.setResCodeOverride(httpRequest, ResCodeEnum.ERROR_0002); // 응답코드 재정의
            throw new UsernameNotFoundException(username);
        }
        return new CustomPrincipal(vo);
    }

    /**
     * 사용자 중복 확인 (회원가입 화면)
     *
     * @param reqVo
     * @return
     */
    public ResBaseVO selectUserDup(ReqDupCheckVO reqVo) {

        ResBaseVO ret = new ResBaseVO().init(ResCodeEnum.SUCCESS.name(), "사용 가능한 아이디 입니다.");

        int cnt = authDAO.selectUserDup(reqVo);
        log.debug("## 사용자 중복 확인 조회 카운트:[{}]", cnt);
        if (cnt > 0) {
            ret.setResCodeEnum(ResCodeEnum.ERROR_0007);
        }

        return ret;
    }

    /**
     * 회원가입
     *
     * @param reqVo
     * @return
     */
    public ResRegisterVO register(ReqRegisterVO reqVo) {

        ResRegisterVO resVo = new ResRegisterVO();

        //------------- 기 등록 회원 여부 체크 - start -------------
        // 화면에서 selectUserDup()를 통해 체크하고 있지만, 그래도 한 번 더 체크해 주자.
        ReqDupCheckVO tmpVo = new ReqDupCheckVO();
        tmpVo.setUsername(reqVo.getUsername());
        int cnt = authDAO.selectUserDup(tmpVo);
        log.debug("## 사용자 중복 확인 조회 카운트:[{}]", cnt);
        if (cnt > 0) {
            resVo.setResCodeEnum(ResCodeEnum.ERROR_0007);
            return resVo; // error stack trace 까지는 찍지 않기 위해서 exception throw가 아닌 return으로 처리.
        }
        //------------- 기 등록 회원 여부 체크 - end ---------------

        // 패스워드 암호화
//        reqVo.setPassword(new BCryptPasswordEncoder().encode(reqVo.getPassword()));
        reqVo.setPassword(passwordEncoder.encode(reqVo.getPassword()));

        // INSERT
        authDAO.insertRegisterInfo(reqVo);

        // 응답에 PK 바인딩
        resVo.setUserManageId(reqVo.getUserManageId());

        return resVo;
    }

    /**
     *　체험회원 등록
     *
     * @param reqVo
     * @return
     */
    public ResTrialRegisterVO trialRegister(ReqTrialRegisterVO reqVo) {

        ResTrialRegisterVO resVo = new ResTrialRegisterVO();
        reqVo.setTrialPeriod(Constants.TRIAL_PERIOD);

        // TODO: 계속 체험회원 등록을 하는 것을 막아야 할 경우 추가 구현

        // INSERT
        authDAO.insertTrialRegister(reqVo);

        // 체험회원 전용 토큰 생성
        Map<String, Object> params = new HashMap<>();
        params.put("trialManageId", reqVo.getTrialManageId());
        String accessToken = tokenProvider.generateTokenTrialUser(params);
        resVo.setTrialManageId(reqVo.getTrialManageId());
        resVo.setGrantType(TokenProvider.BEARER_TYPE);
        resVo.setAccessToken(accessToken);
        resVo.setExpireDate(((Date)params.get("accessTokenExpiresIn")).getTime());

        return resVo;
    }

    /**
     * 정회원 인증
     *
     * @param reqVo
     * @return
     */
    public ResFullMemberAuthVO fullmemberAuth(ReqFullMemberAuthVO reqVo) {



        return null;
    }

    /**
     * 상담 신청
     *
     * @param reqVo
     * @return
     */
    public ResVocVO callVoc(ReqVocVO reqVo) {

        ResVocVO resVo = new ResVocVO();

//        vocDAO.

        return resVo;
    }

}
