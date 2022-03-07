package codegurus.auth.vo;

import codegurus.cmm.constants.AuthEnum;
import codegurus.cmm.vo.BaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자정보 VO
 *
 * 	- 테이블 칼럼은 더 많이 있지만, 필요하면 추가하자.
 */
@Getter
@Setter
public class UserVO extends BaseVO {

	@ApiModelProperty(notes = "사용자관리ID", example = "1")
	private String userManageId;

	@ApiModelProperty(notes = "사용자ID", example = "testuser")
	private String userId;

    // spring security 인증 관련 필드명
	@JsonIgnore
    private String username; // 사용자명이 아니라 userId 와 같은 값임.

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String passwordMask;

	@JsonIgnore
	private String userPhoto;

	@ApiModelProperty(notes = "사용자명", example = "이지은")
	private String name;

	@JsonIgnore
	private String namePast; // 개명했을 시 이전 이름

	@JsonIgnore
	private String birth;

	@JsonIgnore
	private String birthYear;

	@JsonIgnore
	private String gender;

	@JsonIgnore
	private String cellphone;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String zipcode;

	@JsonIgnore
	private String address;

	@JsonIgnore
	private String addressDetail;

	@JsonIgnore
	private String termofuseAgreeOrnot;
	@JsonIgnore
	private String personalinfoAgreeOrnot;
	@JsonIgnore
	private String promotionAgreeOrnot;
	@JsonIgnore
	private String appPushAgreeOrnot;
	@JsonIgnore
	private String locationinfoAgreeOrnot;
	@JsonIgnore
	private String termofuseAgreeDate;
	@JsonIgnore
	private String personalinfoAgreeDate;
	@JsonIgnore
	private String promotionAgreeDate;
	@JsonIgnore
	private String appPushAgreeDate;
	@JsonIgnore
	private String locationinfoAgreeDate;

	@JsonIgnore
	private String soundtrackPlayOrnot;
	@JsonIgnore
	private String guideActivateOrnot;
	@JsonIgnore
	private String themeMode;

	@JsonIgnore
	private String lastPassword;

	@JsonIgnore
	private String lastLoginDate;

	@JsonIgnore
	private String loginFailCount;

	@JsonIgnore
	private String loginFailDate;

	@JsonIgnore
	private String lockOrnot;

	@JsonIgnore
	private String modifyDate;

	@JsonIgnore
	private String regDate;

	@JsonIgnore
	private String deleteOrnot;

	// 추가 정보
	@JsonIgnore
	private String parentName;

	@JsonIgnore
	private String parentBirth;

	@JsonIgnore
	private String authCode;

	// 체험회원 정보
	@JsonIgnore
	private String trialEndDate;

}
