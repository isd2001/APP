package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 자녀 연결 응답 VO
 */
@Getter
@Setter
public class ResConnectChildVO extends ResBaseVO {

    @ApiModelProperty(notes = "현재 부모계정과 연결되어 있는 자녀들의 목록", example = "[]", position = 1)
    private List<ChildInfoVO> childenList;
}
