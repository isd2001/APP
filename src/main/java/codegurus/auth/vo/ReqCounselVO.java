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
 * 상담신청 요청 VO
 */
@Getter
@Setter
public class ReqCounselVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "이지동", position = 1)
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20140511", position = 2)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 5)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_GENDER)
    private String gender;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "박주미", position = 3)
    @NotBlank
    @Size(min = 2, max = 20)
    private String parentName;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01056781234", position = 5)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    private String parentCellphone;

    @ApiModelProperty(notes = "SMS 인증 확인 성공 후 응답으로 받은 토큰", required = true, example = "7Ymv68kOyED3qdDXAlfPUt5lgqX5n1AQSpQjAjdKSI7SDcCj6%2FiW0yqLFEhFfbZBJWonV1bfXXFheGic1LDOeQ%3D%3D", position = 6)
    @NotBlank
    private String smsToken;

    // VOC 파라미터에 정의된 드러나지 않은 필드들
    private String type;
    private String zipcode;
    private String zipcodeSq;
    private String addr1;
    private String addr2;
    private String prodId1;
    private String acptDt;

}
