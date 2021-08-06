package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 (학부모) 응답 VO
 */
@Getter
@Setter
public class ResRegisterParentVO extends ResBaseVO {

    private String userManageId;
}
