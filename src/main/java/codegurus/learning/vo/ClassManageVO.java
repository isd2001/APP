package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 수업 관리 VO
 */
@Getter
@Setter
public class ClassManageVO extends ReqBaseVO {

    @ApiModelProperty(notes = "수업 관리 교사 ID", example = "1")
    private String legacyTcherId;

    @ApiModelProperty(notes = "수업 관리 온라인 과목 스케줄 ID", example = "1")
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "수업 관리 ID", example = "1")
    private String classManageId;

    @ApiModelProperty(notes = "수업 출석 이력 ID", example = "1")
    private String classAttendHistoryId;
}

