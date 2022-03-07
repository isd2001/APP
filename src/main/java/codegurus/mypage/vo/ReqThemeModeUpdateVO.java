package codegurus.mypage.vo;

import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 테마 모드 수정 요청 VO
 */
@Getter
@Setter
public class ReqThemeModeUpdateVO extends ReqBaseVO {

    @ApiModelProperty(notes = "테마 모드, 프론트에서 상품별로 구분하는 값", example = "1")
    @NotBlank
    private String themeMode;

    @ApiModelProperty(notes = "상품 ID", example = "1")
    private String productId = ProductEnum.상품_스마트독서.getProductId();

    @ApiModelProperty(notes = "등록 ID", hidden = true)
    private String userManageId;
}
