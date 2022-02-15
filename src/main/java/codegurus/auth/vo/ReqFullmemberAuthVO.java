package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 계약정보 조회 (정회원 인증 전) 요청 VO
 */
@Setter
public class ReqFullmemberAuthVO extends ReqContractInfoVO {

    @ApiModelProperty(notes = "암호화된 사용자번호 (회원가입API의 응답을 그대로 전송)", example = "Mr8zSeC5fJVfVoj6sbgxMQ%3D%3D", position = 1)
    protected String userManageIdEnc;

    // 현재로서는 사용되지 않는 필드
    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 5)
    @NotBlank
    @Pattern(regexp= StringUtil.REGEX_GENDER)
    protected String gender;

    @ApiModelProperty(notes = "이메일주소", required = false, example = "abc@inter.net", position = 7)
    @Email
    protected String email;

    @ApiModelProperty(notes = "우편번호", required = false, example = "05869", position = 8)
    @Pattern(regexp=StringUtil.REGEX_ZIPCODE)
    protected String zipcode;

    @ApiModelProperty(notes = "주소", required = false, example = "서울특별시 강동구 진황도로 189(둔촌동)", position = 9)
    @Size(min = 0, max = 200)
    protected String address;

    @ApiModelProperty(notes = "상세주소", required = false, example = "101동 1405호", position = 10)
    @Size(min = 0, max = 200)
    protected String addressDetail;

    public String getGender() {
        return gender;
    }
    public String getEmail() {
        return email;
    }
    public String getZipcode() {
        return zipcode;
    }
    public String getAddress() {
        return address;
    }
    public String getAddressDetail() {
        return addressDetail;
    }

    // 기타 getter
    public String getUserManageIdEnc() {
        return userManageIdEnc;
    }
}
