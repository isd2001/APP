package codegurus.mypage.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.ContentsHistoryVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ApiModelProperty(example = "student01", notes = "사용자(학생) ID")
    private String username;
    
    @ApiModelProperty(example = "이지은", notes = "학생 이름")
    private String name;

    @ApiModelProperty(example = "F", notes = "성별 (남:M, 여:F)")
    private String gender;

    @ApiModelProperty(example = "20151111", notes = "학생 생년월일")
    private String birth;

    @ApiModelProperty(example = "박주미", notes = "부모 이름")
    private String parentName;

    @ApiModelProperty(example = "19840222", notes = "부모 생년월일")
    private String parentBirth;

    @ApiModelProperty(example = "01", notes = "권한 코드(01:정회원, 02:일반회원, 03:체험회원)")
    private String authCode;

    @ApiModelProperty(example = "20210929101010", notes = "체험회원 만료일, 체험회원 혹은 일반회원이 체험회원토큰을 받은 경우 이 값이 날라감 만료후에는 null")
    private String trialEndDate;

    @ApiModelProperty(example = "Y", notes = "마케팅 정보 동의 여부(Y, N)")
    private String promotionAgreeOrnot;
}
