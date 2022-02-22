package codegurus.learning.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.validation.DateCheck;
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

    @ApiModelProperty(notes = "콘텐츠 이력 ID",  required = false, hidden=true)
    private String contentsHistoryId;

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", example = "1")
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "콘텐츠 ID", example = "1")
    private String contentsId;

    @ApiModelProperty(notes = "요약하기 그리기 파일 ID", required = false, example = "FILE_000000000000011")
    private String summaryDoingDrawingFileId;

    @ApiModelProperty(notes = "요약하기 음성 파일 ID", required = false, example = "FILE_000000000000011")
    private String summaryDoingVoiceFileId;

    @ApiModelProperty(notes = "템플릿 인스턴스 이력 리스트")
    private List<TemplateInstHistorySaveVo> templateInstHistorySaveList;

    @ApiModelProperty(notes = "시작날짜", example="20210806111111")
    @DateCheck(format = Constants.DF14)
    private String startDate;

    @ApiModelProperty(notes = "수업 준비 이력")
    private ClassPreparationHistoryVO classPreparationHistory;

    @ApiModelProperty(hidden = true)
    private String userManageId;
}
