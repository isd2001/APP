package codegurus.board.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 클라이언트 버전 체크 요청 VO
 */
@Getter
@Setter
public class ReqClientVersionCheckVO {

    @ApiModelProperty(notes="클라이언트 유형(ex: aos,ios 등)", example = "ios", position = 1)
    private String clientType;
}
