package codegurus.oneline.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 한줄평 등록 응답 VO
 */
@Getter
@Setter
public class ResOnelineSaveVO extends ResBaseVO {
    @ApiModelProperty(notes = "한줄평 ID", example = "1")
    private String onelinereviewId;
}
