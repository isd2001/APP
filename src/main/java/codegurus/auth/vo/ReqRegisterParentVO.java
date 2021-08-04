package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원가입 요청 VO (학부모 회원가입일 경우)
 */
@Getter
@Setter
public class ReqRegisterParentVO extends ReqRegisterVO {

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

}
