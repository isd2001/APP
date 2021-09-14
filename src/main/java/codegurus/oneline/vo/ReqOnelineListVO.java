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

    @ApiModelProperty(notes = "회원 ID - 내가 좋아요를 한 한줄평 정보 조회를 위한 회원 ID", hidden=true)
    private String userManageId;

    @ApiModelProperty(notes = "등록 ID", hidden = true)
    private String regId;
}
