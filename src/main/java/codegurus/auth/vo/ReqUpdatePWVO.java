package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.RegEx;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 패스워드 변경 요청 VO
 */
@Getter
@Setter
public class ReqUpdatePWVO extends ReqBaseVO {

    @ApiModelProperty(notes = "패스워드", required = true, example = "student01!@", position = 1)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_USER_PW)
    private String password;

    @JsonIgnore
    private String userManageId;

    @JsonIgnore
    private String passwordMask;
}
