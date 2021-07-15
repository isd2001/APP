package codegurus.study.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 한줄평 목록 조회 응답 > 리스트 요소 VO
 */
@Getter
@Setter
public class ResOnelineListElemVO extends ResBaseVO {

    @ApiModelProperty(notes = "한줄평 ID", example = "1", position = 1)
    private String onelinereviewId;

    @ApiModelProperty(notes = "책 ID", example = "1", position = 2)
    private String bookId;

    @ApiModelProperty(notes = "한줄평 내용", example = "그것은 좋은 책이었습니다. ^^", position = 3)
    private String onelinereviewContent;

    @ApiModelProperty(notes = "별점", example = "5", position = 4)
    private String score;

    @ApiModelProperty(notes = "좋아요 클릭 YN (내가 좋아요를 누른 한줄평일 경우 'Y', 좋아요 누르지 않은 한줄평일 경우 'N')", example = "Y", position = 5)
    private String likeYn;

    @ApiModelProperty(notes = "등록자 userManageId", example = "1", position = 6)
    private String userManageId;

    @ApiModelProperty(notes = "등록자 userId", example = "testuser", position = 7)
    private String userId;

    @ApiModelProperty(notes = "등록자명", example = "이지은", position = 8)
    private String name;

    @ApiModelProperty(notes = "등록일시", example = "20210630111111", position = 9)
    private String regDate;
}
