package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 템플릿 인스턴스 이력 VO
 */
@Getter
@Setter
public class TemplateInstHistoryVO {

    @ApiModelProperty(notes="템플릿 인스턴스 이력 ID")
    private String templateInstHistoryId;

    @ApiModelProperty(notes="콘텐츠 이력 ID")
    private String contentsHistoryId;

    @ApiModelProperty(notes="템플릿 인스턴스 ID")
    private String templateInstId;

    @ApiModelProperty(notes="정답 상태")
    private String correctanswerStatus;
}
