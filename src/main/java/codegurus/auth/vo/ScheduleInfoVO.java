package codegurus.auth.vo;

import codegurus.cmm.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 자녀 정보 VO
 */
@Getter
@Setter
public class ScheduleInfoVO extends BaseVO {

    private String userId;
    private String userManageId;
    private String birth;
    private String userStatusCode;
    private String productId;
    private String authCode;
    private String eduStatCd;
    private String eduStatCdNm;
    private String NoClassChagDt;
    private int value;
}
