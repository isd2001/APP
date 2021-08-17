package codegurus.auth.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.constants.ProjectConstants;
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
 * 계약정보 조회 (정회원 인증 전) 요청 VO
 */
@Setter
public class ReqContractInfoVO extends ReqBaseVO {

    @ApiModelProperty(notes = "암호화된 사용자번호 (회원가입API의 응답을 그대로 전송)", required = true, example = "Mr8zSeC5fJVfVoj6sbgxMQ%3D%3D", position = 1)
    @NotBlank
    protected String userManageIdEnc;

    @ApiModelProperty(notes = "이름", required = true, example = "최효린", position = 2)
    @NotBlank
    @Size(min = 2, max = 20)
    protected String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20020929", position = 3)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    protected String birth;

    // 현재로서는 사용되지 않는 필드
    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 5)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_GENDER)
    protected String gender;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "방성안", position = 4)
    @NotBlank
    @Size(min = 2, max = 20)
    protected String parentName;

    @ApiModelProperty(notes = "부모 생년월일", required = true, example = "19710703", position = 5)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    protected String parentBirth;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01096058088", position = 6)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    protected String parentCellphone;

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

    @JsonIgnore
    protected String productId = ProjectConstants.PRODUCT_ID;


    // 여백 때문에 정회원인증이 실패하지 않도록 trim 보강.
    public String getName() {
        return StringUtil.trim(name);
    }
    public String getBirth() {
        return StringUtil.trim(birth);
    }
    public String getParentName() {
        return StringUtil.trim(parentName);
    }
    public String getParentBirth() {
        return StringUtil.trim(parentBirth);
    }
    public String getParentCellphone() {
        return StringUtil.trim(parentCellphone);
    }

    // 기타 getter
    public String getUserManageIdEnc() {
        return userManageIdEnc;
    }
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
    public String getProductId() {
        return productId;
    }
}
