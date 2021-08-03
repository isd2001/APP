package codegurus.oneline.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 한줄평 목록 조회 요청 VO
 */
@Getter
@Setter
public class ReqOnelineListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", example ="139")
    private String bookId;
}
