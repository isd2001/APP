package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.constants.*;
import codegurus.cmm.exception.CustomException;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.JsonUtil;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.SystemUtil;
import codegurus.cmm.util.WebUtil;
import codegurus.cmm.vo.req.ReqAuthVO;
import codegurus.cmm.vo.res.ResAuthVO;
import codegurus.cmm.vo.res.ResBaseVO;
import codegurus_ext.voc.VocDAO;
import com.google.common.collect.ImmutableMap;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


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
	private EgovEnvCryptoService cryptoService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private AuthDAO authDAO;

    @Autowired
    private VocDAO vocDAO;

    @Autowired
    private HttpServletRequest httpRequest;

	@Value("#{new Integer('${sms.cert.timeout.minute}')}")
	private int smsCertTimeoutMinute;

	@Value("#{new Boolean('${rest.response.sms.certnumber}')}")
	private boolean restResponseSmsCertnumber;


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

        // TODO: 이 회원이 사용할 수 있는 온라인상품/과목/월 조회

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
            WebUtil.setResCodeOverride(httpRequest, ResCodeEnum.INFO_0009); // 응답코드 재정의
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
            ret.setResCodeEnum(ResCodeEnum.INFO_0011);
        }

        return ret;
    }

    /**
     * 회원가입
     *
     * @param reqVo
     * @return
     */
    public <T extends ReqRegisterBaseVO> ResRegisterVO register(T reqVo) {

        ResRegisterVO resVo = new ResRegisterVO();

        //------------- 기 등록 회원 여부 체크 - start -------------
        // 화면에서 selectUserDup()를 통해 체크하고 있지만, 그래도 한 번 더 체크해 주자.
        ReqDupCheckVO tmpVo = new ReqDupCheckVO();
        tmpVo.setUsername(reqVo.getUsername());
        int cnt = authDAO.selectUserDup(tmpVo);
        log.debug("## 사용자 중복 확인 조회 카운트:[{}]", cnt);
        if (cnt > 0) {
            throw new CustomException(ResCodeEnum.INFO_0011);
        }
        //------------- 기 등록 회원 여부 체크 - end ---------------

        String passwordRaw = reqVo.getPassword();
		// 마스킹된 패스워드의 암호화 저장을 위한 처리 (비밀번호 찾기 화면에서 사용자에게 패스워드를 노출해 주어야 하는 요건 때문에 추가함.)
		reqVo.setPasswordMask(cryptoService.encrypt(StringUtil.maskPW(passwordRaw)));
        // 패스워드 해싱
        reqVo.setPassword(passwordEncoder.encode(passwordRaw));

        // INSERT (사용자관리)
        authDAO.insertRegisterInfo(reqVo);

        // INSERT (사용자 권한 매핑)
        authDAO.insertUserAuth(reqVo);

        // 웅답에 암호화된 사용자관리ID 바인딩 (이후 정회원인증을 위해서)
        resVo.setUserManageIdEnc(cryptoService.encrypt(reqVo.getUserManageId()));

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

        // TODO: VOC 호출

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
     * 상담 신청
     *
     * @param reqVo
     * @return
     */
    public ResCounselVO reqCounsel(ReqCounselVO reqVo) {

        ResCounselVO resVo = new ResCounselVO();

        log.debug("## reqVo:[{}]", JsonUtil.toJson(reqVo));

        // smsToken 체크
        SmsToken smsToken = JsonUtil.toObject(cryptoService.decrypt(reqVo.getSmsToken()), SmsToken.class);
        log.debug("## smsToken:[{}]", JsonUtil.toJson(smsToken));
        if(! smsToken.getName().equals(StringUtil.trim(reqVo.getParentName()))){
            throw new CustomException(ResCodeEnum.INFO_0013);
        }
        if(! smsToken.getCellphone().equals(StringUtil.trim(reqVo.getParentCellphone()))){
            throw new CustomException(ResCodeEnum.INFO_0014);
        }

        // db 조회 테스트 코드 - TODO: 삭제
        List<Map<String, String>> vocTestList = vocDAO.selectVocDbTest();
        log.debug("## vocTestList:[{}]", JsonUtil.toJson(vocTestList));

        // TODO: VOC 프로시저 호출

        return resVo;
    }

    /**
     * 계약정보 조회 (정회원 인증 전)
     *
     * @param reqVo
     * @return
     */
    public ResContractInfoVO getContractInfo(ReqContractInfoVO reqVo) {

        ResContractInfoVO resVo = new ResContractInfoVO();

        List<ResContractInfoElemVO> list = authDAO.selectContractInfo(reqVo);
        resVo.setList(list);

        return resVo;
    }

    /**
     * 정회원 인증
     *
     * @param reqVo
     * @return
     */
    public ResFullmemberAuthVO fullmemberAuth(ReqFullmemberAuthVO reqVo) {

        ResFullmemberAuthVO resVo = new ResFullmemberAuthVO();

        String userManageId = cryptoService.decrypt(reqVo.getUserManageIdEnc());
        log.debug("## userManageId:[{}]", userManageId);

        // 교육계약 사본 조회
        List<ResContractInfoElemVO> list = authDAO.selectContractInfo(reqVo);
        if (list.size() == 0) {
            throw new CustomException(ResCodeEnum.INFO_0016);
        }

        // 토큰 대신 userManageIdEnc 를 사용하자.
        // 토큰의 개인정보와 대조 - TODO: 이 제약조건이 과하면 삭제하자.
//        UserVO userVo = cacheService.getTokenUser();
//        log.debug("## 정회원인증 > 토큰 개인정보 대조 - 토큰 사용자명:[{}], 파라미터 사용자 명:[{}], 토큰 생년월일:[{}], 파라미터 생년월일:[{}]", userVo.getName(), reqVo.getName(), userVo.getBirth(), reqVo.getBirth());
//        if(! (userVo.getName().equals(reqVo.getName()) && userVo.getBirth().equals(reqVo.getBirth()))){
//            throw new CustomException(ResCodeEnum.INFO_0017);
//        }

        // 방급 가입한 회원정보와 대조 - TODO: 이 제약조건이 과하면 삭제하자.
        UserVO userVo = commonDAO.selectUserByUserManageId(userManageId);
        log.debug("## userVo:[{}]", JsonUtil.toJson(userVo));
        log.debug("## 정회원인증 > 개인정보 대조 - 회원정보 사용자명:[{}], 파라미터 사용자 명:[{}], 회원정보 생년월일:[{}], 파라미터 생년월일:[{}]", userVo.getName(), reqVo.getName(), userVo.getBirth(), reqVo.getBirth());
        if(! (userVo.getName().equals(reqVo.getName()) && userVo.getBirth().equals(reqVo.getBirth()))){
            throw new CustomException(ResCodeEnum.INFO_0017);
        }

        // 정회원 여부 판단
        boolean isFullmember = false;
        for(ResContractInfoElemVO vo : list){
            if(EduStatCdEnum.수업중.getCode().equals(vo.getEduStatCd())){
                isFullmember = true;
                break;
            }
        }

        // 정회원이 아닐 경우 흐름 중단
        if (! isFullmember) {
            throw new CustomException(ResCodeEnum.INFO_1000.name(), ProjectConstants.PRODUCT_NAME+ " 관련 오프라인 상품중 수업중인 건이 존재하지 않으므로 정회원인증을 중단합니다.");
        }

        // 사용자과목 레코드 생성
        for(ResContractInfoElemVO vo : list){
            authDAO.insertUserSubject(ImmutableMap.of("userManageId", userManageId, "intgEduCntrId", vo.getIntgEduCntrId(), "regId", userManageId));
        }

        // 사용자권한 변경 (학생일반회원 -> 학생정회원)
        int updated = authDAO.updateUserAuth(ImmutableMap.of("authId", AuthEnum.학생정회원.getAuthId(), "modifyId", userManageId, "userManageId", userManageId, "productId", ProjectConstants.PRODUCT_ID));
        SystemUtil.checkUpdatedCount(updated, 1);

        return resVo;
    }

    /**
     * ID 찾기
     *
     * @param reqVo
     * @return
     */
    public ResFindIDVO findId(ReqFindIDVO reqVo) {

        ResFindIDVO resVo = new ResFindIDVO();

        // 이름 생년월일이 필수값으로 변경되었으므로 회원정보를 직접 조회하자.
        String userId = authDAO.selectUserId(reqVo);
        if (userId == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        resVo.setUsername(StringUtil.maskID(userId));

        // 예전 기획에서 생각하던 부분.
        // TODO: SAP 테이블 조회 => 자녀 개인정보 조회 => 자녀 ID 획득
        // TODO: 일반회원은 어떻게 할 것인가?

        return resVo;
    }

    /**
     * 패스워드 찾기
     *
     * @param reqVo
     * @return
     */
    public ResFindPWVO findPw(ReqFindPWVO reqVo) {

        ResFindPWVO resVo = new ResFindPWVO();

        String pw = authDAO.selectUserPassword(reqVo);
        if (pw == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        resVo.setPassword(cryptoService.decrypt(pw));

        return resVo;
    }

    /**
     * SMS 인증번호 요청
     *
     * @param reqVo
     * @return
     */
    public ResSmsCertVO reqSmsCert(ReqSmsCertVO reqVo) {

        ResSmsCertVO resVo = new ResSmsCertVO();

        String certNumber = StringUtil.getRandom6Digits();
        reqVo.setCertNumber(certNumber);

        // TODO: SMS 발송

        // SMS 인증 테이블 저장
        authDAO.insertSmsCert(reqVo);

        // 응답값 바인딩
        resVo.setSmsCertId(reqVo.getSmsCertId());

        // sms 인증 테스트 간소화를 위해 응답에 sms 인증번호를 실어준다.
        if (restResponseSmsCertnumber) { // 운영환경에서는 사용하지 않도록 프로퍼티를 조정하자.
            resVo.setCertNumber(certNumber);
        }

        return resVo;
    }

    /**
     * SMS 인증번호 확인
     *
     * @param reqVo
     * @return
     */
    public ResSmsCertCfmVO cfmSmsCert(ReqSmsCertCfmVO reqVo) {

        ResSmsCertCfmVO resVo = new ResSmsCertCfmVO();

        // smsCertId로 sms_cert 레코드 조회
        reqVo.setSmsCertTimeoutMinute(smsCertTimeoutMinute);
        Map<String, String> smsCert = authDAO.selectSmsCert(reqVo);
        log.debug("## smsCert:[{}]", JsonUtil.toJson(smsCert));

        // smsCertId 에 해당하는 레코드가 없을 경우
        if (smsCert == null) {
            throw new CustomException(ResCodeEnum.WARN_0001);
        }
        // SMS 인증 유효시간 체크
        if("Y".equals(smsCert.get("timeoutYN"))){
            throw new CustomException(ResCodeEnum.INFO_0008);
        }
        // 인증번호 불일치
        if(! StringUtil.trim(reqVo.getCertNumber()).equals(StringUtil.trim(smsCert.get("cert_number")))){ // select 결과가 map에 담길 때는 camel 변환이 없는 듯.
            throw new CustomException(ResCodeEnum.INFO_0005);
        }
        // 이름 불일치
        if(! StringUtil.trim(reqVo.getName()).equals(StringUtil.trim(smsCert.get("name")))){
            throw new CustomException(ResCodeEnum.INFO_0006);
        }
        // 핸드폰번호 불일치
        if(! StringUtil.trim(reqVo.getCellphone()).equals(StringUtil.trim(smsCert.get("cellphone")))){
            throw new CustomException(ResCodeEnum.INFO_0007);
        }

        // smsToken 생성
        String smsToken = new SmsToken(reqVo.getSmsCertId(), reqVo.getName(), reqVo.getCellphone()).json();
        log.debug("## smsToken:[{}]", JsonUtil.toJson(smsToken));

        // sms_cert update (인증성공 날짜)
        authDAO.updateSmsCert(reqVo);

        resVo.setSmsToken(cryptoService.encrypt(smsToken)); // smsToken을 암호화 하여 응답에 바인딩

        return resVo;
    }

    /**
     * 자녀 연결
     *
     * @param reqVo
     * @return
     */
    public ResConnectChildVO connectChild(ReqConnectChildVO reqVo) {

        ResConnectChildVO resVo = new ResConnectChildVO();

        // 자녀회원 select
        UserVO userVo = commonDAO.selectUserByUserId(reqVo.getChildUsername());
        if (userVo == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        // 자녀 패스워드 대조
        boolean match = passwordEncoder.matches(StringUtil.trim(reqVo.getChildPassword()), userVo.getPassword());
        log.debug("## 자녀 매칭 결과:[{}]", match);
        if (! match) {
            throw new CustomException(ResCodeEnum.INFO_0010);
        }

        String userManageIdParent = cacheService.getUserManageId();

        Map<String, String> qp = new HashMap<>();
        qp.put("userManageFamilyId", userManageIdParent);
        qp.put("userManageId", userVo.getUserManageId());
        qp.put("relationCode", reqVo.getRelationCode());

        // 기 연결된 자녀인지 체크
        String connYN = authDAO.selectConnectedFamilyYN(qp);
        log.debug("## 자녀 기 연결 여부:[{}]", connYN);
        if("Y".equals(connYN)){
            // 해당 부모의 자녀목록 조회
            resVo.setChildenList(authDAO.selectChildrenByParent(userManageIdParent));
            resVo.setResCodeEnum(ResCodeEnum.INFO_0015);
            return resVo; // 흐름 종료
        }

        // 가족관계 매핑 등록
        authDAO.insertFamilyRelation(qp);
//        String familyRelationId = StringUtil.trim(qp.get("familyRelationId")); // 지금은 딱히 필요 없어 보여 주석처리
//        log.debug("## familyRelationId:[{}]", familyRelationId);

        // 해당 부모의 자녀목록 조회
        resVo.setChildenList(authDAO.selectChildrenByParent(userManageIdParent));

        return resVo;
    }
}
