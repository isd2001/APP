package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * SMS 인증요청 응답 VO
 */
@Getter
@Setter
public class ResSmsCertVO extends ResBaseVO {

    // SMS_CERT 테이블 PK
    @ApiModelProperty(notes = "SMS 확인 키", example = "1", position = 1)
    private String smsCertId;

    @ApiModelProperty(notes = "SMS 인증번호 (오직 테스트 간소화만을 위해 추가한 프로퍼티. 운영환경에서는 응답으로 오지 않는다.)", required = true, example = "759810", position = 2)
    private String certNumber;
}
