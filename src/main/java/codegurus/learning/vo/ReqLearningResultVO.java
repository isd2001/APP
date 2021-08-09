package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 결과 조회 요청 VO
 */
@Getter
@Setter
public class ReqLearningResultVO extends ReqBaseVO {

    @ApiModelProperty(notes = "콘텐츠 이력 ID", example = "1")
    private String contentsHistoryId;
}
