package codegurus.auth.vo;

import codegurus.cmm.util.StringUtil;
import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 체험회원 정보 조회 응답 VO
 */
@Getter
@Setter
public class ResTrialUserVO extends ResBaseVO {

    @ApiModelProperty(notes = "체험 관리 ID", example = "1", position = 1)
	private String trialManageId;

    @ApiModelProperty(notes = "이름", example = "이지은", position = 2)
	private String name;

    @ApiModelProperty(notes = "생년월일", example = "20141111", position = 3)
	private String birth;

    @ApiModelProperty(notes = "성별", example = "M", position = 4)
	private String gender;

    @ApiModelProperty(notes = "부모 이름", example = "박주미", position = 5)
	private String parentName;

    @ApiModelProperty(notes = "부모 생년월일", example = "19801212", position = 6)
	private String parentBirth;

    @ApiModelProperty(notes = "부모 핸드폰번호", example = "01056781234", position = 7)
	private String parentCellphone;

    @ApiModelProperty(notes = "우편번호", example = "05869", position = 8)
    private String zipcode;

    @ApiModelProperty(notes = "주소", example = "서울특별시 강동구 진황도로 189(둔촌동)", position = 9)
    private String address;

    @ApiModelProperty(notes = "상세주소", example = "101동 1405호", position = 10)
    private String addressDetail;    
    
    @ApiModelProperty(notes = "체험 시작일", example = "20210701", position = 8)
	private String trialStartDate;

    @ApiModelProperty(notes = "체험 종료일", example = "20210708", position = 9)
	private String trialEndDate;

    @ApiModelProperty(notes = "체험기간 유효 여부", example = "Y", position = 10)
	private String validPeriod;

}
