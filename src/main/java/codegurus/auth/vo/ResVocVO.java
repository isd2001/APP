package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 상담신청 응답 VO
 */
@Getter
@Setter
public class ResVocVO extends ResBaseVO {

    private String grantType;
}
