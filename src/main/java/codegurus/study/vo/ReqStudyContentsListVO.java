package codegurus.study.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 콘텐츠 요청 VO
 */
@Getter
@Setter
public class ReqStudyContentsListVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", example = "111")
    private String bookId = "";

}
