package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 콘텐츠 이력 VO
 */
@Getter
@Setter
public class ContentsHistoryVO {

    @ApiModelProperty(notes = "콘텐츠 이력")
    private String contentsHistoryId;

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID")
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "회원 ID")
    private String regId;

    @ApiModelProperty(notes = "요약하기 그리기 파일 ID")
    private String summaryDrawingFileId;

    @ApiModelProperty(notes = "요약하기 음성 파일 ID")
    private String summaryVoiceFileId;

    @ApiModelProperty(notes = "콘텐츠 정보")
    private ContentsVO contents;
}
