package twopro;

import egovframework.rte.fdl.idgnr.impl.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 테스트 클래스
 *
 * @author 이프로
 */
@Slf4j
public class Test2Pro {

    public static void main(String[] args){

//        test01();
//        test02();
//        test03();
//        test04();
        test05();
    }

    private static void test05() {

        boolean ret = "01026683306".matches("^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$");
        log.debug("## ret:[{}]", ret);
    }

    private static void test04() {

        String plain = "testuser";
        String enc = new BCryptPasswordEncoder().encode(plain);

        log.debug("## enc:[{}]", enc);
    }

    private static void test03() {

        String s = "codegurus-hansol-forever-codegurus-hansol-forever-codegurus-hansol-forever";
//        Y29kZWd1cnVzLWhhbnNvbC1mb3JldmVyLWNvZGVndXJ1cy1oYW5zb2wtZm9yZXZlci1jb2RlZ3VydXMtaGFuc29sLWZvcmV2ZXI=
        String enc = Base64.encode(s.getBytes());
        System.out.println(enc);
    }

    private static void test02() {

        String s = "insert /* SampleDAO.insertTest */\t\tinto test_table\t\t(\t\t\tname,\t\t\tage\t\t)\t\tvalues\t\t(\t\t\t'너부리',\t\t \t11\t\t)";
        s = s.replaceAll("\\s+", " ");
        System.out.println(s);
    }

    private static void test01() {

        String s = "select /* SampleDAO.selectTestList */\n" +
                "\t\t\t*\n" +
                "\t\tfrom test_table";

        s = s.replaceAll("\\n", "");

        System.out.println(s);
    }
}
