package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 콘텐츠 인스턴스 VO
 */
@Getter
@Setter
public class TemplateInstVO {
    @ApiModelProperty(notes = "템플릿 인스턴스 ID", example = "1")
    private String templateInstId;

    @ApiModelProperty(notes = "템플릿 ID", example = "1")
    private String templateId;

    @ApiModelProperty(notes = "콘텐츠 ID", example = "4")
    private String contentsId;

    @ApiModelProperty(notes = "JSON", example = "")
    private String json;

    @ApiModelProperty(notes = "순서", example = "1")
    private String order;

    @ApiModelProperty(notes = "유형", example = "어휘")
    private String type;

    @ApiModelProperty(notes = "난이도", example = "중")
    private String difficulty;

    @ApiModelProperty(notes = "문항분류 카테고리 ID")
    private String cateId;

    @ApiModelProperty(notes = "문항분류 카테고리")
    private CateVO cate;
}
