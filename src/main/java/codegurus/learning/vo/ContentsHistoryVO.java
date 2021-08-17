package codegurus.learning.vo;

import egovframework.com.cmm.service.FileVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String userManageId;

    @ApiModelProperty(notes = "요약하기 그리기 파일 ID")
    private String summaryDoingDrawingFileId;

    @ApiModelProperty(notes = "책 썸네일 파일 정보", example = "{}")
    private FileVO drawingFile;

    @ApiModelProperty(notes = "요약하기 음성 파일 ID")
    private String summaryDoingVoiceFileId;

    @ApiModelProperty(notes = "책 썸네일 파일 정보", example = "{}")
    private FileVO voiceFile;

    @ApiModelProperty(notes = "학습 완료일")
    private String endDate;

    @ApiModelProperty(notes = "템플릿 인스턴스 이력 정보")
    private List<TemplateInstHistoryVO> templateInstHistoryList;

    @ApiModelProperty(notes = "사전 목록")
    private List<TemplateInstVO> templateInstList;

    @ApiModelProperty(notes = "책 정보")
    private BookVO book;

    @ApiModelProperty(notes = "문항 점수")
    private List<QuestionScoreVO> questionScoreList;
}
