package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 오늘의 학습 책 요청 VO
 */
@Getter
@Setter
public class ReqLearningBookVO extends ReqBaseVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", required = true, example = "1")
    private String onlineSubjectScheduleId;
}

