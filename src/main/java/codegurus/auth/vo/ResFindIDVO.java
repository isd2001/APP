package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * ID 찾기 응답 VO
 */
@Getter
@Setter
public class ResFindIDVO extends ResBaseVO {

    private String username;
}
