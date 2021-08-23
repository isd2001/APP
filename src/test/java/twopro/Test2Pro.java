package twopro;

import codegurus.cmm.util.StringUtil;
import egovframework.rte.fdl.idgnr.impl.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

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
//        test05();
//        test06();
//        test07();
//        test08();
        test09();
    }

    private static void test09() {

//          int pgressNum = 10;
//        int pgressNum = 40;
//        int pgressNum = 50;
//        int pgressNum = 60;
//        int pgressNum = 130;
//        int pgressNum = 160;
//        int pgressNum = 170;
//        int pgressNum = 500;
//        int pgressNum = 1010;
//        int pgressNum = 1910;

//        PgressVO vo = PgressNumEnumRC.getOnlinePgressInfo(pgressNum);
//        System.out.println(vo);
    }

    private static void test08() {

//        String s = "1234567a901234567890";
//        String s = "abcd9";
//        String s = "abcd9가나";
//        String s = "8abcd9a";

//        String s = "1234567890123456";
//        String s = "(a23456789012345";
        String s = "34#$*^11111111@";

//        Pattern ptn = Pattern.compile(StringUtil.REGEX_USER_ID);
        Pattern ptn = Pattern.compile(StringUtil.REGEX_USER_PW);
        boolean ret = ptn.matcher(s).matches();
        System.out.println(ret);
    }

    private static void test07() {

        String ret = StringUtil.getRandom6Digits();
        log.debug("## ret:[{}]", ret);
    }

    private static void test06() {

        String s = "abcd1234가나다라";

//        String ret = StringUtil.maskRight(s, StringUtil.MASK_CNT_RIGHT_ID);
//        String ret = StringUtil.maskRight(s, StringUtil.MASK_CNT_RIGHT_PW);
        String ret = StringUtil.mask(s, 0, 5);

        log.debug("## ret:[{}]", ret);
    }

    private static void test05() {

        boolean ret = "01026683306".matches("^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$");
        log.debug("## ret:[{}]", ret);
    }

    private static void test04() {

        String plain = "testuser";
        String enc = new BCryptPasswordEncoder().encode(plain);

        log.debug("## enc:[{}]", enc);
//        $2a$10$Y0iIsyWhnmM6XK2GVORxveZiZbk8CSCL9vk/BGvUdLksUjDRwsgJm
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
