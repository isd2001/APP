package codegurus.learning.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 콘텐츠 이력 저장 응답 VO
 */
@Getter
@Setter
public class ResLearningContentsHistorySaveVO extends ResBaseVO {

    @ApiModelProperty(notes = "콘텐츠 이력 ID", example = "1")
    private String contentsHistoryId;
}
