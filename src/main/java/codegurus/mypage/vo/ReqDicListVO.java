package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 나의 사전 목록 요청 VO
 */
@Getter
@Setter
public class ReqDicListVO extends ReqBaseVO {

    @ApiModelProperty(hidden = true)
    private String userManageId;
}
