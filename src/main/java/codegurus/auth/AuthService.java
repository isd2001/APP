package codegurus.auth;

import codegurus.auth.vo.CustomPrincipal;
import codegurus.auth.vo.UserVO;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.constants.ResCodeEnum;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.WebUtil;
import codegurus.cmm.vo.req.ReqAuthVO;
import codegurus.cmm.vo.res.ResAuthVO;
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

import javax.servlet.http.HttpServletRequest;


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
}
