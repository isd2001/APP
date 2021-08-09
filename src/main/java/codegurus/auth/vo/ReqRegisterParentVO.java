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
 * 회원가입 요청 VO (학부모 회원가입일 경우)
 *
 *  - 최초 ReqResterVO 를 상속받을 생각이었으나, 이용약관 등 일부 필드들의 유효성검사 기준 등이 다를 수 있어서 별도로 작성함.
 */
@Getter
@Setter
public class ReqRegisterParentVO extends ReqRegisterBaseVO {

    // 권한구분
    @JsonIgnore
    private final int authId = AuthEnum.학부모.getAuthId();   // user_auth insert용
    @JsonIgnore
    private final String authCode = AuthEnum.학부모.getAuthCode(); // mybatis 분기 처리용

    @ApiModelProperty(notes = "이름", required = false, example = "김수현", position = 3)
    @Size(min = 0, max = 20)
    protected String name;

    @ApiModelProperty(notes = "생년월일", required = false, example = "19851212", position = 4)
    @DateCheck(format = Constants.DF8)
    protected String birth;

    @ApiModelProperty(notes = "성별", required = false, example = "F", position = 5)
    @Pattern(regexp = StringUtil.REGEX_GENDER)
    protected String gender;

    @ApiModelProperty(notes = "핸드폰번호", required = false, example = "01012345678", position = 6)
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
    private String addressDetail;

    // 일단 요건이 없으므로 주석처리
//    @ApiModelProperty(notes = "서비스 이용 약관 동의여부", required = true, example = "Y", position = 6)
//    @NotBlank
//    @Pattern(regexp = StringUtil.REGEX_YN)
//    protected String termofuseAgreeOrnot;
//    @ApiModelProperty(notes = "개인정보 수집 및 이용에 대한 동의여부", required = true, example = "Y", position = 7)
//    @NotBlank
//    @Pattern(regexp = StringUtil.REGEX_YN)
//    protected String personalinfoAgreeOrnot;
//    @ApiModelProperty(notes = "마케팅 활용을 위한 개인정보 수집 및 이용에 대한 동의여부", required = false, example = "Y", position = 8)
//    @Pattern(regexp = StringUtil.REGEX_YN)
//    protected String promotionAgreeOrnot;

}
