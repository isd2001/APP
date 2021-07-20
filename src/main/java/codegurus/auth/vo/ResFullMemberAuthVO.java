package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 정회원 인증 응답 VO
 */
@Getter
@Setter
public class ResFullMemberAuthVO extends ResBaseVO {

    private String grantType;
}
