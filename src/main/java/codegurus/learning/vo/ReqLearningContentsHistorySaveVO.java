package codegurus.learning.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 학습 콘텐츠 이력 저장 요청 VO
 */
@Getter
@Setter
public class ReqLearningContentsHistorySaveVO extends ReqBaseVO {

    @ApiModelProperty(notes = "콘텐츠 이력 ID", example = "1")
    private String contentsHistoryId;

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", example = "1")
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "요약하기 그리기 파일 ID", example = "FILE_000000000000011")
    private String summaryDoingDrawingFileId;

    @ApiModelProperty(notes = "요약하기 음성 파일 ID", example = "FILE_000000000000011")
    private String summaryDoingVoiceFileId;

    @ApiModelProperty(notes = "템플릿 인스턴스 이력 리스트")
    private List<TemplateInstHistorySaveVo> templateInstHistorySaveList;

    @ApiModelProperty(hidden = true)
    private String regId;
}
