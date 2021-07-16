package codegurus.oneline.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 한줄평 조회 요청 VO
 */
@Getter
@Setter
public class ReqOnelineVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", example="111", position = 1)
    String bookId = "";

    @ApiModelProperty(hidden = true)
    private String regId;
}
