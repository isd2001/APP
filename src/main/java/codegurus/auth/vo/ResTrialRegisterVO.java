package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 체험회원 등록 응답 VO
 */
@Getter
@Setter
public class ResTrialRegisterVO extends ResBaseVO {

    private String trialManageId;
    private String grantType;
    private String accessToken;
    private long expireDate;
}
