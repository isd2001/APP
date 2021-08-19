package codegurus.mypage.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.validation.DateCheck;
import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 회원정보 수정 요청 VO
 */
@Getter
@Setter
public class ReqUserUpdateVO extends ReqBaseVO {

    @ApiModelProperty(hidden = true)
    private String userManageId;

    @ApiModelProperty(example = "이지은", notes = "이름", required = true, position = 2)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "20151111", notes = "생년월일", required = true, position = 3)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

}
