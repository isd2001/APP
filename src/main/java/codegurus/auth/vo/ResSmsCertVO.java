package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * SMS 인증요청 응답 VO
 */
@Getter
@Setter
public class ResSmsCertVO extends ResBaseVO {

    private String smsCertId;
}
