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

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원가입 요청 VO
 */
@Getter
@Setter
public class ReqRegisterVO extends ReqBaseVO {

    // insert 결과를 획득하기 위해 필요
    @JsonIgnore
    protected String userManageId;

    // 현재 회원가입 주체가 학부모이면 true
    @JsonIgnore
    protected boolean parent;

    // 권한구분
    @JsonIgnore
    protected int authId;

    // username, password 는 spring security 기본 필드명이기 때문에 일단 따라가 본다.
    @ApiModelProperty(notes = "ID", required = true, example = "student11", position = 1)
    @Pattern(regexp = StringUtil.REGEX_USER_ID)
    protected String username;

    @ApiModelProperty(notes = "패스워드", required = true, example = "student11!@", position = 2)
    @Pattern(regexp = StringUtil.REGEX_USER_PW)
    protected String password;

    // 평문 패스워드의 뒤 몇 글자를 마스킹 한 후, 암호화한 필드 (추후 비밀번호 찾기에서 사용하기 위해)
    @JsonIgnore
    protected String passwordMask;

    @ApiModelProperty(notes = "이름", required = true, example = "이지은", position = 3)
    @NotBlank
    @Size(min = 0, max = 20)
    protected String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20141111", position = 4)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    protected String birth;

    @ApiModelProperty(notes = "성별", required = false, example = "F", position = 5)
    @Pattern(regexp = StringUtil.REGEX_GENDER)
    protected String gender;

    @ApiModelProperty(notes = "서비스 이용 약관 동의여부", required = true, example = "Y", position = 6)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_YN)
    protected String termofuseAgreeOrnot;

    @ApiModelProperty(notes = "개인정보 수집 및 이용에 대한 동의여부", required = true, example = "Y", position = 7)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_YN)
    protected String personalinfoAgreeOrnot;

    @ApiModelProperty(notes = "마케팅 활용을 위한 개인정보 수집 및 이용에 대한 동의여부", required = false, example = "Y", position = 8)
    @Pattern(regexp = StringUtil.REGEX_YN)
    protected String promotionAgreeOrnot;

    // 아직까지 요건이 없는 필드인 듯
//    @ApiModelProperty(notes = "위치정보 동의 여부", required = false, example = "Y", position = 14, hidden = true)
//    @Pattern(regexp=StringUtil.REGEX_YN)
//    protected String locationinfoAgreeOrnot;

}
