package codegurus.mypage.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.validation.DateCheck;
import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ApiModelProperty(example = "이지은", notes = "이름", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "20151111", notes = "생년월일", required = true)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(example = "Y", notes = "앱 푸시 동의 여부")
    private String appPushAgreeOrnot;

}
