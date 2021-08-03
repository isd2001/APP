package codegurus.auth.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 중복 확인 (회원가입 화면) 요청 VO
 */
@Getter
@Setter
public class ReqDupCheckVO extends ReqBaseVO {

    @ApiModelProperty(example = "student01", required = true, position = 1)
    @NotBlank
    private String username;
}
