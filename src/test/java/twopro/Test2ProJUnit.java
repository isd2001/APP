package twopro; // 테스트코드에서도 log4j를 사용하고 싶다면 패키지는 꼭 필요한 듯.

import codegurus.cmm.constants.Constants;
import codegurus.cmm.jwt.TokenProvider;
import codegurus.cmm.ssh.SSHTunnelSAP;
import codegurus.cmm.ssh.SSHTunnelVOC;
import codegurus.cmm.util.JsonUtil;
import codegurus_ext.voc.VocDAO;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JUnit 테스트
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:egovframework/spring/com/**/context-*.xml" })
//@ContextConfiguration(locations = { "classpath*:egovframework/spring/com/**/context-*.xml", "classpath*:WEB-INF/config/egovframework/springmvc/egov-com-*.xml" }) // 두 번째 그룹은 web쪽 context인데, 잘 적용되었는지는 모름.
@ActiveProfiles({"session", "maria", "swagger"}) // spring profile
@WebAppConfiguration("src/main/webapp") // 기본값이면 안해줘도 될 듯
public class Test2ProJUnit {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private EgovEnvCryptoService cryptoService;

    @Autowired
    private VocDAO vocDAO;

    private static SSHTunnelVOC sshTunnelVOC;


    @Test
    public void testCallVocProcedure(){

        Map<String, Object> qp = new HashMap<>();

        qp.put("type", "A");
        qp.put("cust_nm", "박주리");
        qp.put("zipcode", "05869");
        qp.put("zipcode_sq", Constants.우편번호시퀀스_기본값);
        qp.put("addr1", "서울특별시 강동구 진황도로 189(둔촌동)");
        qp.put("addr2", "101동 1405호");
        qp.put("hdph", "01012345678");
        qp.put("child_nm1", "이지은");
        qp.put("child_brt1", "20151111");
        qp.put("child_sx1", "F");
        qp.put("prod_id1", "Z001");
        qp.put("acpt_dt", "스마트독서 VOC 테스트");

//        qp.put("rsltcode", "");
//        qp.put("rsltmsg", "");
//        qp.put("type", "A");
//        qp.put("cust_nm", "박주리");
//        qp.put("reg_num", "");
//        qp.put("ipin", "");
//        qp.put("web_id", "");
//        qp.put("zipcode", "05869");
//        qp.put("zipcode_sq", "Constants.우편번호시퀀스_기본값");
//        qp.put("addr1", "서울특별시 강동구 진황도로 189(둔촌동)");
//        qp.put("addr2", "101동 1405호");
//        qp.put("email", "");
//        qp.put("hdph", "01012345678");
//        qp.put("hsph", "");
//        qp.put("child_nm1", "이지은");
//        qp.put("child_brt1", "20151111");
//        qp.put("child_sx1", "F");
//        qp.put("child_nm2", "");
//        qp.put("child_brt2", "");
//        qp.put("child_sx2", "");
//        qp.put("prod_id1", "Z001");
//        qp.put("prod_id2", "");
//        qp.put("prod_gb", "");
//        qp.put("vcher_yn", "");
//        qp.put("acpt_dt", "스마트독서 VOC 테스트");
//        qp.put("choice_item1", "");
//        qp.put("choice_item2", "");
//        qp.put("suppl_addr", "");
        
        vocDAO.callSpSetAcpt(qp);
//        vocDAO.callSpSetAcpt2(qp);
//        vocDAO.callSpSetAcpt3(qp);

        log.debug("## 프로시저 호출 후 파라미터:[{}]", JsonUtil.toJson(qp));
    }


    @Test
    public void test01(){

        log.debug("## tokenProvider:[{}]", tokenProvider);
    }

    /**
     * 전자정부 암호화 테스트
     */
    @Test
    public void test02(){

        String pw = "brucejason*****";

        String encPw = cryptoService.encrypt(pw);
        log.debug("## encPw:[{}]", encPw);

        String decPw = cryptoService.decrypt(encPw);

        log.debug("## decPw:[{}]", decPw);
    }

    /**
     * 테스트 수행 전 처리
     */
    @BeforeClass
    public static void beforeClass01(){

       // 시스템 프로퍼티 셋팅
//        System.setProperty("log4j.configurationFile","C:/Dev/IntelliJ_Work_Hansol/rcms/src/main/resources/log4j2.xml");
        System.setProperty("log4j.configurationFile","classpath*:log4j2.xml"); // 이게 제대로 먹히고 있는건지는 모르겠다. 일단 파일이 root에 있으니 딱히 지정해 주지 않아도 되긴 하다.

        // SSH local port forwarding 설정
        sshTunnelVOC = new SSHTunnelVOC();
    }

    /**
     * 테스트 수행 후 처리
     */
    @AfterClass
    public static void afterClass01(){

        // SSH local port forwarding 해제
        sshTunnelVOC.closeSSH();
    }

    /**
     * 시스템 프로퍼티 셋팅
     */
    @BeforeClass
    public static void setSystemProperties(){
//        System.setProperty("log4j.configurationFile","C:/Dev/IntelliJ_Work_Hansol/rcms/src/main/resources/log4j2.xml");
        System.setProperty("log4j.configurationFile","classpath*:log4j2.xml"); // 이게 제대로 먹히고 있는건지는 모르겠다. 일단 파일이 root에 있으니 딱히 지정해 주지 않아도 되긴 하다.
    }

}
