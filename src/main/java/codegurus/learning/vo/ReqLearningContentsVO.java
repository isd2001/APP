package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 콘텐츠 요청 VO
 */
@Getter
@Setter
public class ReqLearningContentsVO extends ReqBaseVO {

    @ApiModelProperty(notes = "콘텐츠 ID", example = "43")
    private String contentsId = "";

}
