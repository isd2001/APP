package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 서버 데이터 응답 VO
 */
@Getter
@Setter
public class ResServerDataVO extends ResBaseVO {

    @ApiModelProperty(notes = "서버 데이터 Vo", example = "오브젝트")
    private ServerDataVO item;
}
