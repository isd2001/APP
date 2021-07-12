package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
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
public class ResUserVO extends ResBaseVO {

    @ApiModelProperty(notes = "사용자관리ID", example = "1")
	private String userManageId;

    @ApiModelProperty(notes = "사용자ID", example = "testuser")
	private String userId;

	@ApiModelProperty(notes = "사용자명", example = "이지은")
	private String name;

}
