package codegurus.oneline.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 한줄평 조회 응답 VO
 */
@Getter
@Setter
public class ResOnelineVO extends ResBaseVO {

    @ApiModelProperty(notes = "한줄평 ID", example = "26", position = 1)
    String onelinereviewId = "";

    @ApiModelProperty(notes = "책 ID", example = "1", position = 2)
    String bookId = "";

    @ApiModelProperty(notes = "한줄평 내용", example = "한줄평 내용 테스트 좋은 책이었습니다")
    String onelinereviewContent = "";

    @ApiModelProperty(notes = "별점", example = "4")
    @Min(1)
    @Max(5)
    String score = "";

    @ApiModelProperty(notes = "등록 ID", example = "1")
    String regId = "";
}
