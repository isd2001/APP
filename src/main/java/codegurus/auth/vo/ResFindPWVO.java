package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 패스워드 찾기 응답 VO
 */
@Getter
@Setter
public class ResFindPWVO extends ResBaseVO {

    private String password;
}
