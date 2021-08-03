package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 VO
 */
@Getter
@Setter
public class LearningVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", example="2", position = 1)
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "콘텐츠 ID", example="2", position = 2)
    private String contentsId;

    @ApiModelProperty(notes = "책 ID", example = "139", position = 3)
    private String bookId;

    @ApiModelProperty(notes = "책 정보")
    private BookVO book;
}
