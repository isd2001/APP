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
 * 정회원인증 요청 VO
 */
@Getter
@Setter
public class ReqFullMemberAuthVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "이지은", position = 1)
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20141111", position = 2)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    // 교육계약 테이블에 비교할 만한 필드가 없어서 주석처리
//    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 5)
//    @NotBlank
//    @Pattern(regexp=StringUtil.REGEX_GENDER)
//    private String gender;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "박주미", position = 3)
    @NotBlank
    @Size(min = 2, max = 20)
    private String parentName;

    @ApiModelProperty(notes = "부모 생년월일", required = true, example = "19831212", position = 4)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String parentBirth;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01056781234", position = 5)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    private String parentCellphone;

}
