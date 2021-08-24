package codegurus.auth.vo;

import codegurus.cmm.constants.AuthEnum;
import codegurus.cmm.constants.Constants;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.validation.DateCheck;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원가입 (학생) 요청 VO
 */
@Getter
@Setter
public class ReqRegisterVO extends ReqRegisterBaseVO {

    // 권한구분
    @JsonIgnore
    private final String authId = AuthEnum.스마트독서_학생일반회원.getAuthId();   // user_auth insert용
    @JsonIgnore
    private final String authCode = AuthEnum.스마트독서_학생일반회원.getAuthCode(); // mybatis 분기 처리용

    @ApiModelProperty(notes = "이름", required = true, example = "이지은", position = 3)
    @NotBlank
    @Size(min = 0, max = 20)
    private String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20141111", position = 4)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(notes = "성별", required = false, example = "F", position = 5)
    @Pattern(regexp = StringUtil.REGEX_GENDER)
    private String gender;

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

    // 아직까지 요건이 없는 필드인 듯
//    @ApiModelProperty(notes = "위치정보 동의 여부", required = false, example = "Y", position = 14, hidden = true)
//    @Pattern(regexp=StringUtil.REGEX_YN)
//    private String locationinfoAgreeOrnot;

}
