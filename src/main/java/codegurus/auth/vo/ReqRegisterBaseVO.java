package codegurus.auth.vo;

import codegurus.cmm.constants.AuthEnum;
import codegurus.cmm.constants.Constants;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.validation.DateCheck;
import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원가입 요청 Base VO
 */
@Getter
@Setter
public class ReqRegisterBaseVO extends ReqBaseVO {

    // insert 결과를 획득하기 위해 필요
    @JsonIgnore
    protected String userManageId;

    // username, password 는 spring security 기본 필드명이기 때문에 일단 따라가 본다.
    @ApiModelProperty(notes = "ID", required = true, example = "student11", position = 1)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_USER_ID)
    protected String username;

    @ApiModelProperty(notes = "패스워드", required = true, example = "student11!@", position = 2)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_USER_PW)
    protected String password;

    // 평문 패스워드의 뒤 몇 글자를 마스킹 한 후, 암호화한 필드 (추후 비밀번호 찾기에서 사용하기 위해)
    @JsonIgnore
    protected String passwordMask;

}
