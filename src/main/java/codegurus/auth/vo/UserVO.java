package codegurus.auth.vo;

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

    // spring security 인증 관련 필드명
	@JsonIgnore
    private String username; // 사용자명이 아니라 userId 와 같은 값임.

	@JsonIgnore
    private String password;

    @ApiModelProperty(notes = "사용자관리ID", example = "1")
	private String userManageId;

    @ApiModelProperty(notes = "사용자ID", example = "testuser")
	private String userId;

	@ApiModelProperty(notes = "사용자명", example = "이지은")
	private String name;

	@JsonIgnore
	private String userPhoto;

	// TODO: 무슨 필드임?
	@JsonIgnore
	private String namePast;

	@JsonIgnore
	private String birth;

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
	private String userGroupId;

	@JsonIgnore
	private String authId;

	@JsonIgnore
	private String lastPassword;

	@JsonIgnore
	private String lastLoginDate;

	@JsonIgnore
	private String loginFailCount;

	@JsonIgnore
	private String loginFailDate;

	@JsonIgnore
	private String lock;

	@JsonIgnore
	private String userStatus;

	@JsonIgnore
	private String modifyDate;

	@JsonIgnore
	private String regDate;

	@JsonIgnore
	private String deleteOrnot;

}
