package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 콘텐츠 VO
 */
@Getter
@Setter
public class ContentsVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", example="2", position = 1)
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "콘텐츠 ID", example = "1")
    private String contentsId;

    @ApiModelProperty(notes = "템플릿 인스턴스 리스트")
    private List<TemplateInstVO> templateInstList;
}
