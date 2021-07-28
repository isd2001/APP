package codegurus.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 패스워드 찾기 요청 VO
 */
@Getter
@Setter
public class ReqFindPWVO extends ReqFindIDVO {

    @ApiModelProperty(notes = "아이디", required = true, example = "testuser", position = 3)
    @NotBlank
    protected String username;
}
