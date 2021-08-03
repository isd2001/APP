package codegurus.learning.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 학습 콘텐츠 웅답 VO
 */
@Getter
@Setter
public class ResLearningContentsVO extends ResBaseVO {

    @ApiModelProperty(notes = "콘텐츠 상세 정보")
    private ContentsVO item;
}
