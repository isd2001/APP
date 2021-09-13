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
public class ReqFindPWUpdateVO extends ReqFindPWVO {

    @ApiModelProperty(notes = "패스워드", required = true, example = "student01!@", position = 4)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_USER_PW)
    protected String password;
}
