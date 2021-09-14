package codegurus.oneline.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 나의 별점 조회 요청 VO
 */
@Getter
@Setter
public class ReqStarScoreVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", example="147", position = 1)
    String bookId = "";

    @ApiModelProperty(hidden = true)
    private String regId;
}
