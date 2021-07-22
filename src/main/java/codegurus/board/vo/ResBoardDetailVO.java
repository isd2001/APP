package codegurus.board.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 상세 조회 응답 VO
 */
@Getter
@Setter
public class ResBoardDetailVO extends ResBaseVO {

    @ApiModelProperty(notes = "게시판 id", position = 1)
    private String boardId = "";

    @ApiModelProperty(notes = "제목", position = 2)
    private String boardTitle = "";

    @ApiModelProperty(notes = "내용", position = 3)
    private String content = "";

    @ApiModelProperty(notes = "유형", position = 4)
    private String type = "";

    @ApiModelProperty(notes = "조회 수", position = 5)
    private String lookupCount = "";

    @ApiModelProperty(notes = "등록 id", position = 6)
    private String regId = "";

    @ApiModelProperty(notes = "등록 날짜", position = 7)
    private String regDate = "";
}
