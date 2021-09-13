package codegurus.board.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 목록 조회 VO
 */
@Getter
@Setter
public class ReqBoardListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "상품 ID, RC:1, 플라톤2.0:2", example = "1")
    String productId = "1";

    @ApiModelProperty(notes = "유형", example = "공지사항", hidden = true)
    String type = "";
}
