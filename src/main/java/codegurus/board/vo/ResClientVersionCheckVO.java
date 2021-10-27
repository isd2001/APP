package codegurus.board.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 클라이언트 버전 체크 응답 VO
 */
@Getter
@Setter
public class ResClientVersionCheckVO extends ResBaseVO {

    ClientVersionVO item;
}
