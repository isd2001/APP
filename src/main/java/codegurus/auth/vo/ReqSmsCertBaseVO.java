package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * SMS 인증요청 요청 Base VO
 */
@Getter
@Setter
public class ReqSmsCertBaseVO extends ReqBaseVO {

    @ApiModelProperty(notes = "SMS 유형 ('01':상담신청, '02':ID찾기, '03':비밀번호찾기)", required = true, example = "01", position = 3)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_SMS_TYPE)
    protected String smsType;

    @ApiModelProperty(notes = "이름", required = true, example = "박주미", position = 4)
    @NotBlank
    @Size(min = 2, max = 20)
    protected String name;

    @ApiModelProperty(notes = "핸드폰번호", required = true, example = "01056781234", position = 5)
    @NotBlank
    @Pattern(regexp = StringUtil.REGEX_CELLPHONE)
    protected String cellphone;

}
