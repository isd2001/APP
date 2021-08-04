package codegurus.auth.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 자녀 연결 요청 VO
 */
@Getter
@Setter
public class ReqConnectChildVO extends ReqBaseVO {

    @ApiModelProperty(notes = "자녀 ID", required = true, example = "student01", position = 1)
    @NotBlank
    private String childUsername;

    @ApiModelProperty(notes = "자녀 패스워드", required = true, example = "student01!@", position = 2)
    @NotBlank
    private String childPassword;

    // 관계 코드 (학생 기준에서의 관계 (PARENT, FATHER, MOTHER [공통코드]) - 과연 이런 구분들이 필요한지, 필요하다면 어떻게 할지 미정임.
    @JsonIgnore
    private String relationCode = "PARENT"; // 일다 기본값으로 이걸 넣자.

}
