package codegurus.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

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
