package twopro; // 테스트코드에서도 log4j를 사용하고 싶다면 패키지는 꼭 필요한 듯.

import codegurus.cmm.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
    TokenProvider tokenProvider;

    @Test
    public void test01(){

        log.debug("## tokenProvider:[{}]", tokenProvider);
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
