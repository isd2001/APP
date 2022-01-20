package codegurus.learning.vo;

import egovframework.com.cmm.service.FileVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 책 VO
 */
@Getter
@Setter
public class BookVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", example="2", position = 1)
    private String onlineSubjectScheduleId;

    @ApiModelProperty(notes = "콘텐츠 ID", example="2", position = 2)
    private String contentsId;

    @ApiModelProperty(notes = "책 ID", example = "139", position = 3)
    private String bookId;

    @ApiModelProperty(notes = "책 명", example = "그리스 로마 신화 23")
    private String bookTitle;

    @ApiModelProperty(notes = "책 소개", example = "이 책은 좋은 책입니다.")
    private String bookIntroduction;

    @ApiModelProperty(notes = "책 썸네일 atchFileId")
    private String bookThumbnailFileId;

    @ApiModelProperty(notes = "책 썸네일 파일 정보", example = "{}")
    private FileVO bookThumbnail;

    @ApiModelProperty(notes = "교육 카테고리 ID", example = "{}")
    private String cateId; // 교육 카테고리

    @ApiModelProperty(notes = "교육 카테고리 상세", example = "{}")
    private CateVO cate;

    @ApiModelProperty(notes = "작가", example = "박시연")
    private String author;

    @ApiModelProperty(notes = "그림작가", example = "최우빈")
    private String illustrator;

    @ApiModelProperty(notes = "번역작가", example = "제임스")
    private String translator;

    @ApiModelProperty(notes = "출판사", example = "아울북")
    private String publisher;

    @ApiModelProperty(notes = "출판일", example = "20210616")
    private String publicationdate;

    @ApiModelProperty(notes = "ISBN", example = "9788950971588")
    private String isbn;

    @ApiModelProperty(notes = "줄거리", example = "아흔아홉 달 동안 열두 과업을 힘들게 완수하고 홀가분한 몸이 된 헤라클레스한테 아테나가 급히 찾아온다. <생략..>")
    private String outline;

    @ApiModelProperty(notes = "줄거리 음성 atchFileId")
    private String outlineVoiceFileId;

    @ApiModelProperty(notes = "줄거리 음성 파일 정보", example = "{}")
    private FileVO outlineVoice;

    @ApiModelProperty(notes = "트레일러", example = "https://www.youtube.com/watch?v=Bh6Kv2ltEI4")
    private String trailer;

    @ApiModelProperty(notes = "트레일러 활성화 여부", example = "Y")
    private String trailerActiveOrnot;

    @ApiModelProperty(notes = "책 Json", example = "{}")
    private String bookJson;

    @ApiModelProperty(notes = "책 라벨 목록")
    private List<LabelVO> labelList;
}
