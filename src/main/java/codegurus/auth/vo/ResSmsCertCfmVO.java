package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * SMS 인증확인 응답 VO
 */
@Getter
@Setter
public class ResSmsCertCfmVO extends ResBaseVO {

    /**
     * SMS 인증의 위변조/오용 방지를 위해 서버에서 생성해 주는 토큰
     */
    private String smsToken;
}
