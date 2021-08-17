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
public class ReqThisMonthBookVO extends ReqBaseVO {

    @ApiModelProperty(notes = "온라인 과목 ID, 현재 사용자 정보를 가져와서 세팅해야하는데 테스트용으로 넣어둠", example ="1")
    private int onlineSubjectId;

    @ApiModelProperty(notes = "연도", example ="2021", hidden = true)
    private String year;

    @ApiModelProperty(notes = "월 (01~12), 원래는 지금 해당하는 월을 조회해야하는데 테스트용으로 넣어둠", example ="01")
    private String month;
}
