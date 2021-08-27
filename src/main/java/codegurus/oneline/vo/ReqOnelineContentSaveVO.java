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
public class ReqOnelineContentSaveVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", example = "100")
    @NotBlank
    private String bookId;

    @ApiModelProperty(notes = "한줄평 내용", example = "구해줘 카카오프렌즈 경제1 재미있어요.", position = 3)
    @Size(min = 1, max = 50)
    private String onelinereviewContent;
}
