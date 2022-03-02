package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 수업 체크 요청 VO
 */
@Getter
@Setter
public class ReqLearningClassCheckVO extends ReqBaseVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", required = true, example = "1")
    private String onlineSubjectScheduleId;

    @JsonIgnore
    private String userManageId;
    @JsonIgnore
    private String classManageId;
    @JsonIgnore
    private String classAttendHistoryId;
}

