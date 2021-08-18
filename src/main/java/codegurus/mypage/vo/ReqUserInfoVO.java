package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원정보 조회 요청 VO
 */
@Getter
@Setter
public class ReqUserInfoVO extends ReqBaseVO {

    @ApiModelProperty(hidden = true)
    private String userManageId;
}
