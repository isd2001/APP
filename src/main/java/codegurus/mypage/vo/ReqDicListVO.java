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

    @ApiModelProperty(notes="year", example="2021")
    private String year;

    @ApiModelProperty(notes = "month", example="09")
    private String month;

    @ApiModelProperty(hidden = true)
    private String userManageId;
}
