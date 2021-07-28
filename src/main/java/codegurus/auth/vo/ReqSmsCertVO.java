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
 * SMS 인증요청 요청 VO
 */
@Getter
@Setter
public class ReqSmsCertVO extends ReqSmsCertBaseVO {

    // SMS 인증번호
    @JsonIgnore
    private String certNumber;

    // SMS_CERT 테이블 PK
    @JsonIgnore
    private String smsCertId;

}
