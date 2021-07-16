package codegurus.oneline.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 한줄평 좋아요 등록/삭제 요청 VO
 */
@Getter
@Setter
public class ReqOnelineMarkGoodVO extends ReqBaseVO {
    @ApiModelProperty(notes = "명령 (A:좋아요 추가, D:좋아요 제거)", example = "A", position = 1)
    @NotBlank
    @Pattern(regexp="^[AD]$")
    private String cmd;

    @ApiModelProperty(notes = "한줄평 ID", example = "29", position = 2)
    private String onelinereviewId;

    @ApiModelProperty(notes = "한줄평 좋아요 ID (좋아요 제거시에만 요청값 존재)", example = "2", position = 3)
    private String onelinereviewLikeId;

    @ApiModelProperty(hidden = true)
    private String regId;
}
