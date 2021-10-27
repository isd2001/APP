package codegurus.board.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 마케팅 활용 동의 여부 요청 VO
 */
@Getter
@Setter
public class ReqPromotionAgreeVO extends ReqBaseVO {

    @ApiModelProperty(notes = "마케팅 활용 동의 여부 구분(Y:동의, N: 비동의", example = "Y", position = 1)
    @NotBlank
    @Pattern(regexp="^[YN]$")
    private String promotionAgreeOrnot;

    @ApiModelProperty(notes = "등록 ID", hidden = true)
    private String userManageId;
}
