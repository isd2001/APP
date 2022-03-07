package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 앱 푸시 동의 여부 요청 VO
 */
@Getter
@Setter
public class ReqAppPushAgreeVO extends ReqBaseVO {

    @ApiModelProperty(notes = "앱 푸시 동의 여부 구분(Y:동의, N: 비동의)", example = "Y")
    @NotBlank
    @Pattern(regexp="^[YN]$")
    private String appPushAgreeOrnot;

    @ApiModelProperty(notes = "등록 ID", hidden = true)
    private String userManageId;
}
