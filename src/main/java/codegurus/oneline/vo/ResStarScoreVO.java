package codegurus.oneline.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 나의 별점 조회 응답 VO
 */
@Getter
@Setter
public class ResStarScoreVO extends ResBaseVO {

    private OnelineVO item;
}
