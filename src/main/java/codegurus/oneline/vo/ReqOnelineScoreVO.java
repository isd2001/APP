package codegurus.oneline.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 한줄평 등록 요청 VO
 */
@Getter
@Setter
public class ReqOnelineScoreVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", example = "100", position = 2)
    @NotBlank
    private String bookId;

    @ApiModelProperty(notes = "별점", example = "4")
    @Min(1)
    @Max(5)
    String score;
}
