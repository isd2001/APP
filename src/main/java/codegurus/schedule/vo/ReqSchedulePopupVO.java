package codegurus.schedule.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 이달의 도서 팝업 요청 VO
 */
@Getter
@Setter
public class ReqSchedulePopupVO extends ReqBaseVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", required = true, example = "2")
    @NotBlank
    private String onlineSubjectScheduleId;
}
