package codegurus.learning.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 결과 조회 응답 VO
 */
@Getter
@Setter
public class ResLearningResultVO extends ResBaseVO {

    @ApiModelProperty(notes = "학습 결과 정보")
    private ContentsHistoryVO item;
}
