package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 나의 진도 목록 요청 VO
 */
@Getter
@Setter
public class ReqMagnitudeListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "month", example="07")
    private String month;

    @ApiModelProperty(notes = "사용자 관리 ID", example="2")
    private String userManageId;
}
