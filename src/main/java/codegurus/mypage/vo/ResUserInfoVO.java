package codegurus.mypage.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.ContentsHistoryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 회원정보 조회 응답 VO
 */
@Getter
@Setter
public class ResUserInfoVO extends ResBaseVO {

    @ApiModelProperty(example = "student01", notes = "사용자(학생) ID", position = 1)
    private String username;
    
    @ApiModelProperty(example = "이지은", notes = "학생 이름", position = 2)
    private String name;

    @ApiModelProperty(example = "20151111", notes = "학생 생년월일", position = 3)
    private String birth;

    @ApiModelProperty(example = "박주미", notes = "부모 이름", position = 4)
    private String parentName;

    @ApiModelProperty(example = "19840222", notes = "부모 생년월일", position = 5)
    private String parentBirth;
}
