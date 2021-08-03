package codegurus.learning.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 오늘의 학습 책 웅답 VO
 */
@Getter
@Setter
public class ResLearningBookVO extends ResBaseVO {

    @ApiModelProperty(notes = "오늘의 학습 책 정보")
    private BookVO item;
}
