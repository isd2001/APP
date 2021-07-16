package codegurus.oneline.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 한줄평 좋아요 등록/삭제 응답 VO
 */
@Getter
@Setter
public class ResOnelineMarkGoodVO extends ResBaseVO  {
    @ApiModelProperty(notes = "명령", example = "A", position = 1)
    private String cmd;

    @ApiModelProperty(notes = "한줄평 ID", example = "29", position = 2)
    private String onelinereviewId;

    @ApiModelProperty(notes = "한줄평 좋아요 ID", example = "2", position = 3)
    private String onelinereviewLikeId;

    @ApiModelProperty(hidden = true)
    private String regId;
}
