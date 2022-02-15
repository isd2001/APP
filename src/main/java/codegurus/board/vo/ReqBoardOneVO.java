package codegurus.board.vo;

import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 상세 조회 요청 VO
 */
@Getter
@Setter
public class ReqBoardOneVO extends ReqBaseVO {

    @ApiModelProperty(notes = "상품 ID, RC:1, 플라톤2.0:2", example = "1")
    String productId = ProductEnum.상품_스마트독서.getProductId();

    @ApiModelProperty(notes = "유형", example = "공지사항", hidden = true)
    private String type = "";
}
