package codegurus.study.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import egovframework.com.cmm.service.FileVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 오늘의 학습 책 웅답 VO
 */
@Getter
@Setter
public class ResStudyBookVO extends ResBaseVO {

    @ApiModelProperty(notes = "책 ID", example = "1", position = 1)
    private String bookId;

    @ApiModelProperty(notes = "책 명", example = "그리스 로마 신화 23", position = 2)
    private String bookTitle;

    @ApiModelProperty(notes = "책 썸네일 FILE ID", hidden = true)
    private String bookThumbnailFileId;

    @ApiModelProperty(notes = "책 썸네일 파일 정보", example = "{}", position = 3)
    private FileVO bookThumbnail;

    @ApiModelProperty(notes = "줄거리", example = "아흔아홉 달 동안 열두 과업을 힘들게 완수하고 홀가분한 몸이 된 헤라클레스한테 아테나가 급히 찾아온다. <생략..>", position = 4)
    private String outline;

    @ApiModelProperty(notes = "트레일러", example = "https://www.youtube.com/watch?v=Bh6Kv2ltEI4", position = 5)
    private String trailer;

    @ApiModelProperty(notes = "트레일러 활성화 여부", example = "Y", position = 6)
    private String trailerActiveOrnot;

    @ApiModelProperty(notes = "라벨 리스트", position = 7)
    private List<ResLabelListElemVO> labelList;

    @ApiModelProperty(notes = "한줄평 리스트", position=8)
    private List<ResOnelineListElemVO> onelineList;
}
