package codegurus.auth.vo;

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
 * 계약정보 조회 (정회원 인증 전) 요청 VO
 */
@Setter
public class ReqContractInfoVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "최효린", position = 2)
    @NotBlank
    @Size(min = 2, max = 20)
    protected String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20020929", position = 3)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    protected String birth;

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

    // 인증시 모든 상품 다 조회하는것으로
    @ApiModelProperty(notes = "정회원인증 할 상품ID (1:스마트독서, 2:리터러시, 안보낼 경우 모두 확인)", required = false, example = "", position = 10)
    protected String productId = "";

    @JsonIgnore
    protected String userManageId;

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
    public String getProductId() {
        return productId;
    }
    public String getUserManageId() {
        return userManageId;
    }
}
