 package codegurus.mypage.vo;

import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.TemplateInstVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

 /**
 * 나의 사전 VO
 */
@Getter
@Setter
public class MyDicVO {

    @ApiModelProperty(notes = "템플릿 인스턴스 ID")
    private String templateInstId;

    @ApiModelProperty(notes = "템플릿 인스턴스 정보")
    private List<TemplateInstVO>  templateInstList;

    @ApiModelProperty(notes = "책 ID")
    private String bookId;

    @ApiModelProperty(notes = "책 정보")
    private BookVO book;
}
