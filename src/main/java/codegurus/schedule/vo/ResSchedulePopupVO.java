package codegurus.schedule.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 이달의 도서 팝업 응답 VO
 */
@Getter
@Setter
public class ResSchedulePopupVO extends ResBaseVO {

    @ApiModelProperty(notes = "이달의 도서 팝업 정보")
    private ResScheduleVO item;
}
