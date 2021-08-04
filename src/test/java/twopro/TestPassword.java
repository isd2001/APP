package twopro; // 테스트코드에서도 log4j를 사용하고 싶다면 패키지는 꼭 필요한 듯.

import codegurus.cmm.util.StringUtil;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 패스워드 관련 테스트 클래스
 *
 * @author 이프로
 * @version 2021.07
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:egovframework/spring/com/**/context-*.xml" })
@ActiveProfiles({"session", "maria"}) // spring profile
@WebAppConfiguration("src/main/webapp")
public class TestPassword {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EgovEnvCryptoService cryptoService;

    /**
     * 패스워드 해싱 처리 테스트 (단방향)
     */
    @Test
    public void testPasswordEnc(){

        String s = "parent01!@";
        String enc = passwordEncoder.encode(s);
        log.debug("## 패스워드 해싱결과:[{}]", enc);
    }

    /**
     * 전자정부 암호화 테스트
     *
     *  - 마스킹된 패스워드를 암/복호화 하는데 사용
     */
    @Test
    public void testEgovEncDec(){

        String pw = "parent01!@";
        pw = StringUtil.maskPW(pw); // 패스워드 마스킹

        String encPw = cryptoService.encrypt(pw);
        log.debug("## 전자정부 암호화 결과:[{}]", encPw);

        String decPw = cryptoService.decrypt(encPw);
        log.debug("## 전자정부 복호화 결과:[{}]", decPw);
    }


    /**
     * 시스템 프로퍼티 셋팅
     */
    @BeforeClass
    public static void setSystemProperties(){
        System.setProperty("log4j.configurationFile","classpath*:log4j2.xml"); // 이게 제대로 먹히고 있는건지는 모르겠다. 일단 파일이 root에 있으니 딱히 지정해 주지 않아도 되긴 하다.
    }

}
