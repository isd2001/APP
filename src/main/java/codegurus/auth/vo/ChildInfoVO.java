package codegurus.auth.vo;

import codegurus.cmm.vo.BaseVO;
import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 자녀 정보 VO
 */
@Getter
@Setter
public class ChildInfoVO extends BaseVO {

    @ApiModelProperty(notes = "자녀 사용자ID", example = "student01", position = 1)
    private String username;

    @ApiModelProperty(notes = "자녀 명", example = "이지은", position = 2)
    private String name;

    @ApiModelProperty(notes = "자녀 생년월일", example = "20151111", position = 3)
    private String birth;

    @ApiModelProperty(notes = "사용자 권한 명", example = "학생정회원", position = 4)
    private String authTitle;
}
