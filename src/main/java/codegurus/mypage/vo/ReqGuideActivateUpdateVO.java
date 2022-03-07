package codegurus.mypage.vo;

import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 가이드 활성화 여부 수정 요청 VO
 */
@Getter
@Setter
public class ReqGuideActivateUpdateVO extends ReqBaseVO {

    @ApiModelProperty(notes = "가이드 활성화 여부 구분(Y:활성, N: 비활성)", example = "Y")
    @NotBlank
    @Pattern(regexp="^[YN]$")
    private String guideActivateOrnot;

    @ApiModelProperty(notes = "상품 ID", example = "1")
    private String productId = ProductEnum.상품_스마트독서.getProductId();

    @ApiModelProperty(notes = "등록 ID", hidden = true)
    private String userManageId;
}
