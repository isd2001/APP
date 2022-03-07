package codegurus.mypage.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 음원 재생 여부 수정 요청 VO
 */
@Getter
@Setter
public class ReqSoundtrackPlayUpdateVO extends ReqBaseVO {

    @ApiModelProperty(notes = "음원 재생 여부 구분(Y:재생, N: 미재생)", example = "Y")
    @NotBlank
    @Pattern(regexp="^[YN]$")
    private String soundtrackPlayOrnot;

    @ApiModelProperty(notes = "등록 ID", hidden = true)
    private String userManageId;
}
