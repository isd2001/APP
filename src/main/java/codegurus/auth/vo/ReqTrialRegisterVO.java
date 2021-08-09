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
 * 체험회원 등록 요청 VO
 */
@Getter
@Setter
public class ReqTrialRegisterVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "이지금", position = 1)
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20141122", position = 2)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(notes = "성별", required = true, example = "F", position = 3)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_GENDER)
    private String gender;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "박주미", position = 4)
    @NotBlank
    @Size(min = 2, max = 20)
    private String parentName;

    @ApiModelProperty(notes = "부모 생년월일", required = true, example = "19801212", position = 5)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String parentBirth;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01056781234", position = 6)
    @NotBlank
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    private String parentCellphone;

    @ApiModelProperty(notes = "우편번호", required = true, example = "05869", position = 8)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_ZIPCODE)
    private String zipcode;

    @ApiModelProperty(notes = "주소", required = true, example = "서울특별시 강동구 진황도로 189(둔촌동)", position = 9)
    @NotBlank
    @Size(min = 0, max = 200)
    private String address;

    @ApiModelProperty(notes = "상세주소", required = true, example = "101동 1405호", position = 10)
    @NotBlank
    @Size(min = 0, max = 200)
    private String addressDetail;

    @JsonIgnore
    private String trialManageId;

    @JsonIgnore
    private int trialPeriod;

}
