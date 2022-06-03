package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResAuthVO;
import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 응답 VO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResLoginVO extends ResAuthVO {

    @ApiModelProperty(notes = "부모회원 여부(Y:부모회원, N:다른회원)", example = "N")
    private String parentCheck = "N";
    @ApiModelProperty(notes = "부모회원일때 메시지", example = "학부모앱(북터러시)에서 가입하신 ID입니다. 학생앱(한솔플라톤)에서 신규 가입하여 이용해 주세요.")
    private String parentMsg = "";
}
