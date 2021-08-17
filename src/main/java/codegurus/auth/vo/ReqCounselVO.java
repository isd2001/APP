package codegurus.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 상담신청 요청 VO
 */
@Getter
@Setter
public class ReqCounselVO extends ReqVocBaseVO {

    @ApiModelProperty(notes = "상담신청 상세내용", required = true, example = "이러이러한 내용으로 상담을 신청합니다.", position = 10)
    @NotBlank
    private String acptDt;

    @ApiModelProperty(notes = "SMS 인증 확인 성공 후 응답으로 받은 토큰", required = true, example = "7Ymv68kOyED3qdDXAlfPUt5lgqX5n1AQSpQjAjdKSI7SDcCj6%2FiW0yqLFEhFfbZBJWonV1bfXXFheGic1LDOeQ%3D%3D", position = 11)
    @NotBlank
    private String smsToken;

}
