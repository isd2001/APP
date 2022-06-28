package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 패스워드 찾기 요청 VO
 */
@Getter
@Setter
public class ReqAgreeVO {

    @ApiModelProperty(notes = "아이디", required = true, example = "student01", position = 1)
    @NotBlank
    protected String username;

    @ApiModelProperty(notes = "서비스 이용 약관 동의여부", required = true, example = "Y", position = 6)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_YN)
    private String termofuseAgreeOrnot;

    @ApiModelProperty(notes = "개인정보 수집 및 이용에 대한 동의여부", required = true, example = "Y", position = 7)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_YN)
    private String personalinfoAgreeOrnot;

    @ApiModelProperty(notes = "마케팅 활용을 위한 개인정보 수집 및 이용에 대한 동의여부", required = false, example = "Y", position = 8)
    @Pattern(regexp = StringUtil.REGEX_YN)
    private String promotionAgreeOrnot;
}
