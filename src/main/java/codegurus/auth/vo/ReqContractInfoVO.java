package codegurus.auth.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.validation.DateCheck;
import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 계약정보 조회 (정회원 인증 전) 요청 VO
 */
@Setter
public class ReqContractInfoVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "김승주", position = 1)
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20140607", position = 2)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "안유나", position = 3)
    @NotBlank
    @Size(min = 2, max = 20)
    private String parentName;

    @ApiModelProperty(notes = "부모 생년월일", required = true, example = "19870318", position = 4)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String parentBirth;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01071858850", position = 5)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    private String parentCellphone;

    // 여백 때문에 실패하지 않도록 trim 보강.
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

    // 교육계약 테이블에 비교할 만한 필드가 없어서 주석처리
//    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 5)
//    @NotBlank
//    @Pattern(regexp=StringUtil.REGEX_GENDER)
//    private String gender;


}
