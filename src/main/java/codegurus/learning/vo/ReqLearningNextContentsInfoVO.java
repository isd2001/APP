package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 다음 콘텐츠 정보 요청 VO
 */
@Getter
@Setter
public class ReqLearningNextContentsInfoVO extends ReqBaseVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", required = true, example = "1")
    private String onlineSubjectScheduleId;
}

