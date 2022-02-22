package codegurus.learning.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 수업 준비 이력 VO
 */
@Getter
@Setter
public class ClassPreparationHistoryVO {

    @ApiModelProperty(notes = "수업 준비 이력 ID", example="1", hidden = true)
    @JsonIgnore
    private String classPreparationHistoryId;

    @ApiModelProperty(notes = "수업 준비 사용자 ID", example="100000000", hidden = true)
    @JsonIgnore
    private String userManageId;

    @ApiModelProperty(notes = "콘텐츠 ID", example = "1", hidden = true)
    @JsonIgnore
    private String contentsId;

    @ApiModelProperty(notes = "리딩북 완료, (완료:Y,미완료:N)", example = "N")
    private String readingbookComplete;

    @ApiModelProperty(notes = "국어파일 완료, (완료:Y,미완료:N)", example = "N")
    private String koreanfileComplete;
}
