package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 나의 포트폴리오 목록 요청 VO
 */
@Getter
@Setter
public class ReqPortfolioListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "month", example="07")
    private String month;

    @ApiModelProperty(hidden = true)
    private String userManageId;
}