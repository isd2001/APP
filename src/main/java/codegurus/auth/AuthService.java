package codegurus.auth;

import codegurus.auth.vo.*;
import codegurus.cmm.CommonDAO;
import codegurus.cmm.cache.CacheService;
import codegurus.cmm.constants.*;
import codegurus.cmm.exception.CustomException;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.util.*;
import codegurus.cmm.vo.req.ReqAuthVO;
import codegurus.cmm.vo.req.ReqBaseVO;
import codegurus.cmm.vo.res.ResAuthVO;
import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.mypage.MypageDAO;
import codegurus_ext.voc.VocDAO;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
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

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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

    @Autowired
    private MypageDAO mypageDAO;

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

        // 체험회원 토큰이 넘어왔다면 업데이트 쳐준다.
        if(StringUtil.isNotBlank(reqVo.getTrialToken())) {
            try {
                Claims claims = tokenProvider.parseClaims(reqVo.getTrialToken());

                String trialManageId = StringUtil.trim(claims.get("trialManageId"));
                if(StringUtil.isNotBlank(trialManageId) && Integer.parseInt(trialManageId) > 0) {
                    reqVo.setTrialManageId(trialManageId);
                    authDAO.updateTrialManageId(reqVo);
                }
            } catch (Exception e) {
                // 오류가 나더라도 여기는 무시, 제대로된 토큰을 안보냈을꺼기때문에
            }
        }


//         4. RefreshToken 저장 (현재(2021.06) 정책상 사용하지 않음)
//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authentication.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();
//        refreshTokenRepository.save(refreshToken);

        // 앱 푸시 토큰 저장
        if(reqVo.getPushToken() != null && reqVo.getClientType() != null) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", reqVo.getUsername());
            params.put("productId", ProductEnum.상품_스마트독서.getProductId());

            UserVO vo = commonDAO.selectUserByUserId(params);
            reqVo.setUserManageId(vo.getUserManageId());

            authDAO.deleteAppPushToken(reqVo);
            authDAO.insertAppPushToken(reqVo);
        }

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

        Map<String, String> params = new HashMap<>();
        params.put("userId", username);
        params.put("productId", ProductEnum.상품_스마트독서.getProductId());

        UserVO vo = commonDAO.selectUserByUserId(params);
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

        // INSERT (사용자 권한 매핑) 상품별로 다 해줘야함.
        // TODO 상품이 추가될때 수정
        reqVo.setAuthId(AuthEnum.getAuthIdByAuthCode(ProductEnum.상품_스마트독서.getProductId(), "02"));
        authDAO.insertUserAuth(reqVo);
        reqVo.setAuthId(AuthEnum.getAuthIdByAuthCode(ProductEnum.상품_플라톤.getProductId(), "02"));
        authDAO.insertUserAuth(reqVo);

        // 회원가입시 상품별 스케줄 간격 추가
        if(reqVo instanceof ReqRegisterVO
            && StringUtil.isNotBlank(((ReqRegisterVO) reqVo).getGrade())) {
            // 간격값은 현재 나이와 선택한 나이의 차이 개월수를 계산하는것이다.
            int selectGrade = 0;
            int birthGrade = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(((ReqRegisterVO) reqVo).getBirth().substring(0, 4)) + 1;

            switch (((ReqRegisterVO) reqVo).getGrade()) {
                case "예비초등" :
                    selectGrade = 7;
                    break;
                case "1학년" :
                    selectGrade = 8;
                    break;
                case "2학년" :
                    selectGrade = 9;
                    break;
                case "3학년" :
                    selectGrade = 10;
                    break;
                case "4학년" :
                    selectGrade = 11;
                    break;
            }

            // 잘못된 요청
            if(selectGrade != 0) {
                ScheduleIntervalVO scheduleIntervalVO = new ScheduleIntervalVO();
                scheduleIntervalVO.setValue((selectGrade - birthGrade) * 12);
                scheduleIntervalVO.setUserManageId(reqVo.getUserManageId());

                // TODO 상품이 추가될때 수정
                scheduleIntervalVO.setProductId(ProductEnum.상품_스마트독서.getProductId());
                authDAO.insertScheduleInterval(scheduleIntervalVO);

                // 플라톤 상품은 4개 과목이 각각 계약이 따로여서 일단 월별로만 수정이 가능할듯해서 0으로 초기화한다
                scheduleIntervalVO.setProductId(ProductEnum.상품_플라톤.getProductId());
                scheduleIntervalVO.setValue(0);
                authDAO.insertScheduleInterval(scheduleIntervalVO);
            }
        }

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
        LocalDateTime now = LocalDateTime.now();

        long timemills = Timestamp.valueOf(now).getTime();
        String startDate = DateUtil.getLocalDateTimeToString(now, Constants.DF14);
        String endDate = DateUtil.addMinutes(startDate, Constants.DF14, Constants.TRIAL_PERIOD);
        reqVo.setTrialStartDate(startDate);
        reqVo.setTrialEndDate(endDate);

        log.debug("## reqVo:[{}]", JsonUtil.toJson(reqVo));

        // TODO: 계속 체험회원 등록을 하는 것을 막아야 할 경우 추가 구현

        // INSERT
        authDAO.insertTrialRegister(reqVo);

        // VOC 프로시저 호출
        callVocProcedure(reqVo, reqVo.getAcptDt());

        // 체험회원 전용 토큰 생성
        Map<String, Object> params = new HashMap<>();
        params.put("trialManageId", reqVo.getTrialManageId());
        String accessToken = tokenProvider.generateTokenTrialUser(params, timemills);
        resVo.setTrialManageId(reqVo.getTrialManageId());
        resVo.setGrantType(TokenProvider.BEARER_TYPE);
        resVo.setAccessToken(accessToken);
        resVo.setExpireDate(((Date)params.get("accessTokenExpiresIn")).getTime());

        // 회원 토큰이 넘어왔다면 업데이트 쳐준다.
        if(StringUtil.isNotBlank(reqVo.getUserToken())) {
            try {
                Claims claims = tokenProvider.parseClaims(reqVo.getUserToken());

                String username = StringUtil.trim(claims.get(TokenProvider.SUBJECT_KEY));
                if(StringUtil.isNotBlank(username)) {
                    ReqAuthVO reqAuthVO = new ReqAuthVO();
                    reqAuthVO.setUsername(username);
                    reqAuthVO.setTrialManageId(reqVo.getTrialManageId());
                    authDAO.updateTrialManageId(reqAuthVO);
                }
            } catch (Exception e) {
                // 오류가 나더라도 여기는 무시, 제대로된 토큰을 안보냈을꺼기때문에
            }
        }
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
//        List<Map<String, String>> vocTestList = vocDAO.selectVocDbTest();
//        log.debug("## vocTestList:[{}]", JsonUtil.toJson(vocTestList));

        // VOC 프로시저 호출
        callVocProcedure(reqVo, reqVo.getAcptDt());

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

        List<ResContractInfoElemVO> list = getContractInfoSapApi(reqVo);
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

        String userManageId = null;

        // 로그인 중에도 인증에 추가되서 수정함
        if(StringUtil.isNotBlank(reqVo.getUserManageIdEnc())) {
            userManageId = cryptoService.decrypt(reqVo.getUserManageIdEnc());
        } else {
            UserVO check = cacheService.getTokenUser(ProductEnum.상품_스마트독서.getProductId());

            if(check != null) {
                userManageId = check.getUserManageId();
            }
        }

        log.debug("## userManageId:[{}]", userManageId);

        // 교육계약 사본 조회
        List<ResContractInfoElemVO> list = getContractInfoSapApi(reqVo);
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
        Map<String, String> params = new HashMap<>();
        params.put("userManageId", userManageId);
        params.put("productId", ProductEnum.상품_스마트독서.getProductId());
        UserVO userVo = commonDAO.selectUserByUserManageId(params);
        log.debug("## userVo:[{}]", JsonUtil.toJson(userVo));
        log.debug("## 정회원인증 > 개인정보 대조 - 회원정보 사용자명:[{}], 파라미터 사용자 명:[{}], 회원정보 생년월일:[{}], 파라미터 생년월일:[{}]", userVo.getName(), reqVo.getName(), userVo.getBirth(), reqVo.getBirth());
        if(! (userVo.getName().equals(reqVo.getName()) && userVo.getBirth().equals(reqVo.getBirth()))){
            throw new CustomException(ResCodeEnum.INFO_0017);
        }

        // 20210819 요건 변경 - 정회원 여부는 수업중 여부와 무관하다.
/*        // 정회원 여부 판단
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
        }*/

        // 상품코드 리스트를 저장한다
        List<String> productIdList = new ArrayList<>();

        // 사용자과목 레코드 생성
        for(ResContractInfoElemVO vo : list){
            productIdList.add(vo.getProductId());

//            authDAO.insertUserSubject(ImmutableMap.of(
//                    "userManageId", userManageId,
//                    "intgEduCntrId", vo.getIntgEduCntrId(),
//                    "intgCustId", vo.getIntgCustId(),
//                    "intgSubjId", vo.getIntgSubjId(),
//                    "regId", userManageId));

            Map<String, String> vp = new LinkedHashMap<>();
            vp.put("userManageId", userManageId);
            vp.put("intgEduCntrId", vo.getIntgEduCntrId());
            vp.put("intgCustId", vo.getIntgCustId());
            vp.put("intgSubjId", vo.getIntgSubjId());
            vp.put("eduCntrOid", vo.getEduCntrOid());
            vp.put("sapCustId", vo.getSapCustId());
            vp.put("sapSubjId", vo.getSapSubjId());
            vp.put("regId", userManageId);

            // 이미 연결된 계약정보일 수 있어서 확인 필요, 있는 경우에는 스킵
            int cnt = authDAO.selecttUserSubjectCount(vp);

            if(cnt < 1) {
                authDAO.insertUserSubject(vp);
            }
        }

        // 중복제거
        productIdList = productIdList.stream().distinct().collect(Collectors.toList());

        if(productIdList.size() > 0) {
            for(String productId : productIdList) {
                // 새로운 상품의 경우 권한이 없을 수 있어서 조회해서 있는지 확인이 필요, 없는 경우에는 insert
                Map<String, String> vp = new LinkedHashMap<>();
                vp.put("userManageId", userManageId);
                vp.put("productId", productId);
                int cnt = authDAO.selectUserAuthCount(vp);

                if(cnt > 0) {
                    // 사용자권한 변경 (학생일반회원 -> 학생정회원)
                    int updated = authDAO.updateUserAuth(ImmutableMap.of("authId", AuthEnum.getAuthIdByAuthCode(productId, "01"), "modifyId", userManageId, "userManageId", userManageId, "productId", productId));
                    SystemUtil.checkUpdatedCount(updated, 1);
                } else {
                    // 사용자권한 입력 (학생일반회원 -> 학생정회원)
                    ReqRegisterVO vo = new ReqRegisterVO();
                    vo.setUserManageId(userManageId);
                    vo.setAuthId(AuthEnum.getAuthIdByAuthCode(productId, "01"));
                    authDAO.insertUserAuth(vo);
                }
            }
        }

        // 현재 정회원인증시 정회원 인증에 필요한 값들을 제외한 값 (이메일, 주소 관련 정보)등은 따로 어디에 저장할 것인지 정의된 것이 없다.
        // 그래서 해당 정보를 그냥 날려버리는 대신 학생회원 레코드에 저장하도록 하였다. (추후에 어떻게 할 것인지는 논의 필요)
        reqVo.setUserManageId(userManageId);
        authDAO.updateUserInfoByFullmemberAuth(reqVo);

        return resVo;
    }

    /**
     * 계약정보 조회 sap api 추가
     *
     * @param reqVo
     * @return
     */
    public List<ResContractInfoElemVO> getContractInfoSapApi(ReqContractInfoVO reqVo) {

        List<ResContractInfoElemVO> list = authDAO.selectContractInfo(reqVo);

        // 아직 DW에 적재가 안되었을 수 있기 때문에 sap api 추가 연동
        if(list == null || list.size() <= 0) {

            Base64.Encoder encoder = Base64.getEncoder();
            Base64.Decoder decoder = Base64.getDecoder();

            // 생년월일, 전화번호 -를 추가해야함
            String birth = reqVo.getBirth().substring(0, 4) + "-" + reqVo.getBirth().substring(4, 6) + "-" + reqVo.getBirth().substring(6, 8);
            String parentBirth = reqVo.getParentBirth().substring(0, 4) + "-" + reqVo.getParentBirth().substring(4, 6) + "-" + reqVo.getParentBirth().substring(6, 8);
            String parentCellphone = reqVo.getParentCellphone().substring(0, 3) + "-" + reqVo.getParentCellphone().substring(3, 7) + "-" + reqVo.getParentCellphone().substring(7, 11);
            String matnr = "";

            Map<String, String> param = new HashMap<>();
            param.put("productId", reqVo.getProductId());

            List<String> sapSubjIdList = authDAO.selectOnlineSubjectSapMappingList(param).stream().map(el -> String.valueOf(el.get("sap_subject_id"))).distinct().collect(Collectors.toList());;

            for(String sap_subject_id : sapSubjIdList) {
                if(!matnr.equals("")) {
                    matnr += "|" + sap_subject_id;
                } else {
                    matnr += sap_subject_id;
                }
            }
            // TODO test
//            matnr += "|800863|800829|801293";

            String url = "https://hsapi.eduhansol.co.kr/smart/smartPlatonCust"
                    + "?childName=" + new String(encoder.encode(reqVo.getName().getBytes()))
                    + "&childBirth=" + new String(encoder.encode(birth.getBytes()))
                    + "&parentName=" + new String(encoder.encode(reqVo.getParentName().getBytes()))
                    + "&parentBirth=" + new String(encoder.encode(parentBirth.getBytes()))
                    + "&parentTel=" + new String(encoder.encode(parentCellphone.getBytes()))
                    + "&matnr=" + new String(encoder.encode(matnr.getBytes()));

            String responseMessage = "";
            InputStream is = null;
            InputStreamReader isr =  null;
            BufferedReader br = null;
            StringBuffer sb = new StringBuffer();

            try {
                URL httpsUrl = new URL(url);
                HttpsURLConnection conn = (HttpsURLConnection) httpsUrl.openConnection();
                conn.setUseCaches(false);
                conn.setConnectTimeout(40000);
                conn.setDoOutput(true); //post 방식 설정

                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {

                        }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {

                        }
                    }
                };
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                conn.connect();
                responseMessage = conn.getHeaderField(0);

                System.out.println(responseMessage);
                // HTTP/1.1 200 OK 형식의 http 헤더 결과 코드가 출력됩니다.
                is = conn.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String line = null;
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb);
                //html 부분 출력

                JSONObject jsonObject = new JSONObject(sb.toString());
                if(jsonObject.getJSONObject("result") != null && jsonObject.getJSONObject("result").getString("type").equals("Uw==")) {
                    System.out.println("sap api 성공");

                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONObject custInfo = result.getJSONArray("custInfo").getJSONObject(0);

                    // base64 decode
                    String parentSapCustId = new String(decoder.decode(custInfo.getString("KUNNR").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    String sapCustId = new String(decoder.decode(custInfo.getString("KUNNR_C").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);


                    JSONArray subjectInfo = result.getJSONArray("subjectInfo");

                    for(int i = 0; i < subjectInfo.length(); i++) {
                        JSONObject subject = subjectInfo.getJSONObject(i);

                        String eduCntrOid = new String(decoder.decode(subject.getString("VBELN").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                        String sapSubjId = new String(decoder.decode(subject.getString("MATNR").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8).substring(12, 18);

                        Map<String, String> map = new HashMap<>();
                        map.put("CUST_NM", reqVo.getName());
                        map.put("BIRTHDT", reqVo.getBirth());
                        map.put("SAP_CUST_ID", sapCustId);
                        map.put("EDU_STAT_CD", EduStatCdEnum.수업중.getCode());
                        map.put("EDU_STAT_CD_NM", "수업중");
                        map.put("EDU_CNTR_OID", eduCntrOid);
                        map.put("PARENT_CUST_NM", reqVo.getParentName());
                        map.put("PARENT_BIRTHDT", reqVo.getParentBirth());
                        map.put("PARENT_HANDPHONE", reqVo.getParentCellphone());
                        map.put("PARENT_SAP_CUST_ID", parentSapCustId);
                        map.put("SAP_SUBJ_ID", sapSubjId);

                        authDAO.insertEduCntrCp(map);
                    }

                    // 모두 처리후 다시 조회
                    list = authDAO.selectContractInfo(reqVo);
                } else {
                    // 로그인 실패
                    // result 안에 message 에러메시지가 있긴한데 생략
                    throw new CustomException(ResCodeEnum.INFO_0009);
                }
            } catch(Exception e){
                e.printStackTrace();
                throw new CustomException(ResCodeEnum.INFO_0009);
            }
        }
        return list;
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

        UserVO userVO = authDAO.selectUserPassword(reqVo);
        if (userVO == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        resVo.setPassword(cryptoService.decrypt(userVO.getPasswordMask()));

        return resVo;
    }

    /**
     * 패스워드 찾기 후 변경
     *
     * @param reqVo
     * @return
     */
    public ResBaseVO findPwUpdate(ReqFindPWUpdateVO reqVo) {
        ResBaseVO resVo = new ResBaseVO();

        UserVO userVO = authDAO.selectUserPassword(reqVo);
        if (userVO == null) {
            throw new CustomException(ResCodeEnum.INFO_0009);
        }

        ReqUpdatePWVO reqUpdatePWVO = new ReqUpdatePWVO();
        reqUpdatePWVO.setUserManageId(userVO.getUserManageId());

        String passwordRaw = reqVo.getPassword();
        reqUpdatePWVO.setPasswordMask(cryptoService.encrypt(StringUtil.maskPW(passwordRaw))); // 패스워드 마스킹
        reqUpdatePWVO.setPassword(passwordEncoder.encode(passwordRaw)); // 패스워드 해싱

        int updated = mypageDAO.updateUserPassword(reqUpdatePWVO);
        SystemUtil.checkUpdatedCount(updated, 1);

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

        Map<String, String> params = new HashMap<>();
        params.put("userId", reqVo.getChildUsername());
        params.put("productId", ProductEnum.상품_스마트독서.getProductId());

        // 자녀회원 select
        UserVO userVo = commonDAO.selectUserByUserId(params);
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

    /**
     * 사용 가능 상품(스마트독서, 플라톤..) 목록 출력
     *
     * @param reqVo
     * @return
     */
    public ResAvailProdsVO getAvailProds(ReqBaseVO reqVo) {

        ResAvailProdsVO resVo = new ResAvailProdsVO();
        List<String> eduCntrIdList = authDAO.selectUserSubjectList(cacheService.getUserManageId());
        log.debug("## eduCntrIdList:{}", JsonUtil.toJson(eduCntrIdList));

        for(String eduCntrId : eduCntrIdList){

            Map<String, Object> qp = new HashMap<>();
            qp.put("intgEduCntrId", eduCntrId);
            authDAO.callGetOnlineSubjInfo(qp);
            log.debug("## qp:{}", JsonUtil.toJson(qp));
            String eduStatCd = StringUtil.trim(qp.get("eduStatCd"));
            if (EduStatCdEnum.수업중.getCode().equals(eduStatCd)) {
                resVo.getProdIdList().add(StringUtil.trim(qp.get("productId")));
            }
        }

        // TODO: 테스트용 - 삭제할 것 (SAP 학생 데이터가 부실하므로 테스트단계에서는 이렇게 운용)
        resVo.getProdIdList().clear();
        resVo.setProdIdList(ImmutableList.of(ProductEnum.상품_스마트독서.getProductId())); // 스마트독서 무조건 활성화

        return resVo;
    }

    /**
     * VOC 프로시저 호출
     *
     * @param reqVo
     * @param acptDt
     */
    private void callVocProcedure(ReqVocBaseVO reqVo, String acptDt){

        Map<String, Object> vp = new LinkedHashMap<>();
        vp.put("type", reqVo.getType());
        vp.put("cust_nm", reqVo.getParentName());
        vp.put("zipcode", reqVo.getZipcode());
        vp.put("zipcode_sq", reqVo.getZipcodeSq());
        vp.put("addr1", reqVo.getSigungu());

        String addr2 = "";
        if(reqVo.getAddress() != null && reqVo.getSigungu() != null) {
            addr2 = reqVo.getAddress().replace(reqVo.getSigungu(), "").trim();
        }

        vp.put("addr2", addr2);
        vp.put("suppl_addr", reqVo.getAddressDetail());
        vp.put("hdph", reqVo.getParentCellphone());
        vp.put("child_nm1", reqVo.getName());
        vp.put("child_brt1", reqVo.getBirth());
        vp.put("child_sx1", reqVo.getGender());
        vp.put("prod_id1", ProductEnum.getProductEnumByProductId(reqVo.getProductId()).getProdId1());
        vp.put("acpt_dt", acptDt);
        vp.put("choice_item1", "APP");
        vp.put("prod_gb", "platonsmartapp");
        vocDAO.callSpSetAcpt(vp); // 호출
        reqVo.setVocRsltCode(StringUtil.trim(vp.get("rsltcode")));
        reqVo.setVocRsltMsg(StringUtil.trim(vp.get("rsltmsg")));
        log.debug("## VOC 프로시저 호출결과:[{}]", JsonUtil.toJson(vp));

        // 호출결과가 정상이 아닐 경우
        if(! "0".equals(reqVo.getVocRsltCode())){
            throw new CustomException(ResCodeEnum.ERROR_0011, reqVo.getVocRsltMsg());
        }
    }

}
