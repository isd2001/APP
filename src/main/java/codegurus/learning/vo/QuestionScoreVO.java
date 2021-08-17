package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 문항 점수 VO
 */
@Getter
@Setter
public class QuestionScoreVO {

    @ApiModelProperty(notes = "문항 점수 ID")
    private String questionScoreId;

    @ApiModelProperty(notes = "카테고리 ID")
    private String cateId;

    @ApiModelProperty(notes = "카테고리 정보")
    private CateVO cate;

    @ApiModelProperty(notes = "온라인 과목 ID")
    private String onlineSubjectId;

    @ApiModelProperty(notes = "정답 상태")
    private String correctanswerStatus;

    @ApiModelProperty(notes = "배정 점수")
    private String assignmentScore;

    @ApiModelProperty(notes = "산정 점수")
    private String calculationScore;

    @ApiModelProperty(notes = "점수 백분율")
    private String scorePercent;
}
