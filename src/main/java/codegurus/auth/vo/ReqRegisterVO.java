package codegurus.auth.vo;

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
    private String userManageId;

    // username, password 는 spring security 기본 필드명이기 때문에 일단 따라가 본다.
    @ApiModelProperty(notes = "ID", required = true, example = "testuser", position = 1)
    @NotBlank
    private String username;

    @ApiModelProperty(notes = "패스워드", required = true, example = "testuser", position = 2)
    @NotBlank
    private String password;

    @ApiModelProperty(notes = "이름", required = false, example = "이지은", position = 3)
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @ApiModelProperty(notes = "생년월일", required = false, example = "20141111", position = 4)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(notes = "성별", required = false, example = "M", position = 5)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_GENDER)
    private String gender;

    @ApiModelProperty(notes = "서비스 이용 약관 동의여부", required = true, example = "Y", position = 6)
    @Pattern(regexp=StringUtil.REGEX_YN)
    private String termofuseAgreeOrnot;

    @ApiModelProperty(notes = "개인정보 수집 및 이용에 대한 동의여부", required = true, example = "Y", position = 7)
    @Pattern(regexp=StringUtil.REGEX_YN)
    private String personalinfoAgreeOrnot;

    @ApiModelProperty(notes = "마케팅 활용을 위한 개인정보 수집 및 이용에 대한 동의여부", required = false, example = "Y", position = 8)
    @Pattern(regexp=StringUtil.REGEX_YN)
    private String promotionAgreeOrnot;

    // 아직까지 요건이 없는 필드인 듯
//    @ApiModelProperty(notes = "위치정보 동의 여부", required = false, example = "Y", position = 14, hidden = true)
//    @Pattern(regexp=StringUtil.REGEX_YN)
//    private String locationinfoAgreeOrnot;



    // 이하 나중에 필요할 지 몰라서 백업
/*    @ApiModelProperty(notes = "핸드폰번호", required = false, example = "01012345678", position = 6)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    private String cellphone;

    @ApiModelProperty(notes = "이메일주소", required = false, example = "abc@inter.net", position = 7)
    @Email
    private String email;

    @ApiModelProperty(notes = "우편번호", required = false, example = "05869", position = 8)
    @Pattern(regexp=StringUtil.REGEX_ZIPCODE)
    private String zipcode;

    @ApiModelProperty(notes = "주소", required = false, example = "서울특별시 강동구 진황도로 189(둔촌동)", position = 9)
    @Size(min = 0, max = 200)
    private String address;

    @ApiModelProperty(notes = "상세주소", required = false, example = "101동 1405호", position = 10)
    @Size(min = 0, max = 200)
    private String addressDetail;*/

}
