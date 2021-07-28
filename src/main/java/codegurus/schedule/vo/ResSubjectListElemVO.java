package codegurus.schedule.vo;

import codegurus.cmm.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 온라인 과목 목록 조회 응답 > 리스트 요소 VO
 */
@Getter
@Setter
public class ResSubjectListElemVO extends BaseVO {

    @ApiModelProperty(notes = "온라인 과목 ID")
    private int onlineSubjectId;

    @ApiModelProperty(notes = "서비스 명")
    private String serviceTitle;

    @ApiModelProperty(notes = "과목 명")
    private String subjectTitle;

}
