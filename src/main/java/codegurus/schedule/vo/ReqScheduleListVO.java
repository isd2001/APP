package codegurus.schedule.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 도서 일정 목록 조회 VO
 */
@Getter
@Setter
public class ReqScheduleListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "온라인 과목 ID", example ="1")
    private int onlineSubjectId;
}
