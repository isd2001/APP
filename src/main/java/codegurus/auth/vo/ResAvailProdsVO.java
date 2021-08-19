package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용 가능 상품(스마트독서, 플라톤..) 목록 출력 응답 VO
 */
@Getter
@Setter
public class ResAvailProdsVO extends ResBaseVO {

    @ApiModelProperty(example = "[1, 2]", notes = "상품ID 목록 (1:스마트독서, 2:플라톤 ...)", position = 1)
    private List<String> prodIdList = new ArrayList<>();
}
