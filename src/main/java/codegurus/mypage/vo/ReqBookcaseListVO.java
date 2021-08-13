package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 나의 책장 목록 요청 VO
 */
@Getter
@Setter
public class ReqBookcaseListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "사용자 관리 ID", example="2")
    private String userManageId;
}
