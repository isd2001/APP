package codegurus.schedule.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 도서 일정 목록 조회 VO
 */
@Getter
@Setter
public class ReqThisMonthBookVO extends ReqBaseVO {
    @ApiModelProperty(notes = "상품ID", example ="1")
    private String productId = "1";

    @ApiModelProperty(notes = "온라인 과목 ID, 테스트때 썼던 컬럼 삭제 대상", example ="1")
    private int onlineSubjectId;

    @ApiModelProperty(notes = "연도, 테스트때 썼던 컬럼 삭제 대상", example ="2021", hidden = true)
    private String year;

    @ApiModelProperty(notes = "월 (01~12), 테스트때 썼던 컬럼 삭제 대상", example ="01")
    private String month;

    @JsonIgnore
    private String userManageId;

}
