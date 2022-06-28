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

    @ApiModelProperty(example = "Y", notes = "이용약관 동의 여부 (서비스 이용 약관)(Y, N)")
    private String termofuseAgreeOrnot;

    @ApiModelProperty(example = "Y", notes = "개인정보 동의 여부 (개인정보 수집 및 이용에 대한 동의)(Y, N)")
    private String personalinfoAgreeOrnot;

    @ApiModelProperty(example = "Y", notes = "마케팅 정보 동의 여부(Y, N)")
    private String promotionAgreeOrnot;

    @ApiModelProperty(example = "Y", notes = "앱 푸시 동의 여부(Y, N)")
    private String appPushAgreeOrnot;

    @ApiModelProperty(example = "Y", notes = "음원 재생 여부(Y, N)")
    private String soundtrackPlayOrnot;

    @ApiModelProperty(example = "Y", notes = "가이드 활성화 여부(Y, N)")
    private String guideActivateOrnot;

    @ApiModelProperty(example = "1", notes = "테마 모드 (1, 2 상품별로 프론트에서 알아서 구분하여 사용)")
    private String themeMode;

    @ApiModelProperty(notes = "학년(예비초등, 1~4학년), 정회원 조회시에만 리턴됨", position = 18)
    protected String grade;

    @ApiModelProperty(notes = "월(1~12), 정회원 조회시에만 리턴됨", position = 19)
    protected String month;

    @ApiModelProperty(example = "APP", notes = "회원가입 방법(앱 회원가입-APP, SAP자동등록-SAP)")
    protected String signupMethod;
}
