package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 서버 데이터 VO
 */
@Getter
@Setter
public class ServerDataVO extends ResBaseVO {

    @ApiModelProperty(notes = "키", example = "키")
    private String key;

    @ApiModelProperty(notes = "값", example = "값")
    private String value;
}
