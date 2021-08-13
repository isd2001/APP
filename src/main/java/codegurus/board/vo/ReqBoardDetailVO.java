package codegurus.board.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 상세 조회 요청 VO
 */
@Getter
@Setter
public class ReqBoardDetailVO extends ReqBaseVO {

    @ApiModelProperty(notes = "게시판 id", example = "1")
    private String boardId = "";

    @ApiModelProperty(notes = "유형", example = "공지사항", hidden = true)
    private String type = "";
}
