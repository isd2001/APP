package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 콘텐츠 VO
 */
@Getter
@Setter
public class ContentsVO {

    @ApiModelProperty(notes = "콘텐츠 ID", example = "1")
    private String contentsId;

    @ApiModelProperty(notes = "콘텐츠 명", example = "테스트 콘텐츠")
    private String contentsTitle;

    @ApiModelProperty(notes = "템플릿 인스턴스 리스트")
    private List<TemplateInstVO> templateInstList;

    @ApiModelProperty(notes = "책 정보")
    private BookVO book;
}
