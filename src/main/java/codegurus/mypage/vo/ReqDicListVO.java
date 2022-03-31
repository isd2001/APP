package codegurus.mypage.vo;

import codegurus.cmm.constants.ProductEnum;
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

    @ApiModelProperty(notes = "상품ID", example = "1")
    protected String productId = ProductEnum.상품_스마트독서.getProductId();
}
