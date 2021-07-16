package codegurus.oneline.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * 한줄평 등록 요청 VO
 */
@Getter
@Setter
public class ReqOnelineSaveVO extends ReqBaseVO {

    @ApiModelProperty(notes = "한줄평 ID", example = "28", position = 1)
    private String onelinereviewId = "";

    @ApiModelProperty(notes = "책 ID", example = "100", position = 2)
    @NotBlank
    private String bookId;

    @ApiModelProperty(notes = "한줄평 내용", example = "구해줘 카카오프렌즈 경제1 재미있어요.", position = 3)
    @Size(min = 1, max = 50)
    private String onelinereviewContent;

    @ApiModelProperty(notes = "별점", example = "4")
    @Min(1)
    @Max(5)
    String score = "";

    @ApiModelProperty(notes = "별점등록 or 한줄평내용 등록 구분(S:별점등록, C: 한줄평 내용 등록", example = "C")
    @NotBlank
    @Pattern(regexp="^[CS]$")
    String saveType = "";

    @ApiModelProperty(hidden = true)
    private String regId;

    @ApiModelProperty(hidden = true)
    private String modifyId;
}
