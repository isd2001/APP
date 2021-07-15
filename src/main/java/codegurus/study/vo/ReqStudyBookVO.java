package codegurus.study.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 오늘의 학습 책 요청 VO
 */
@Getter
@Setter
public class ReqStudyBookVO extends ReqBaseVO {

    @ApiModelProperty(notes = "책 ID", required = true, example = "111")
    private String bookId;
}
