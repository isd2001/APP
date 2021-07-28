package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * SMS 인증확인 요청 VO
 */
@Getter
@Setter
public class ReqSmsCertCfmVO extends ReqSmsCertBaseVO {

    // SMS_CERT 테이블 PK
    @ApiModelProperty(notes = "SMS 확인 키", required = true, example = "1", position = 1)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_DB_PK)
    private String smsCertId;

    // SMS 인증번호
    @ApiModelProperty(notes = "SMS 인증번호", required = true, example = "758065", position = 2)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_SMS_CERT_NUM)
    private String certNumber;

}
