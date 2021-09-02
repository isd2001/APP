package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 콘텐츠 템플릿 인스턴트 이력 저장 VO
 */
@Getter
@Setter
public class TemplateInstHistorySaveVo {

    @ApiModelProperty(notes = "콘텐츠 이력 ID", hidden=true)
    private String contentsHistoryId;

    @ApiModelProperty(notes = "템플릿 인스턴스 ID", example="71")
    private String templateInstId;

    @ApiModelProperty(notes = "정답 상태", example = "1")
    private String correctanswerStatus;

    @ApiModelProperty(hidden = true)
    private String userManageId;


}
