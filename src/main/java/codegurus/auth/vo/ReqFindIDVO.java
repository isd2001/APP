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
 * ID 찾기 요청 VO
 */
@Getter
@Setter
public class ReqFindIDVO extends ReqBaseVO {

    @ApiModelProperty(notes = "학생 이름", required = true, example = "이지은", position = 2)
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @ApiModelProperty(notes = "학생 생년월일", required = true, example = "20130111", position = 3)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;


    // 예전 기획에서 생각하던 부분.
//    @ApiModelProperty(notes = "부모 이름", required = true, example = "박주미", position = 3)
//    @NotBlank
//    @Size(min = 2, max = 20)
//    protected String parentName;
//    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01056781234", position = 5)
//    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
//    protected String parentCellphone;
}
