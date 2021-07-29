package codegurus.schedule.vo;

import codegurus.cmm.vo.BaseVO;
import codegurus.study.vo.ResStudyBookVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 도서 일정 목록 조회 응답 > 리스트 요소 VO
 */
@Getter
@Setter
public class ResScheduleVO extends BaseVO {

    @ApiModelProperty(notes = "온라인 과목 스케줄 ID", example = "1", position = 1)
    private int onlineSubjectScheduleId;

    @ApiModelProperty(notes = "온라인 과목 ID", example = "1", position = 2)
    private int onlineSubjectId;

    @ApiModelProperty(notes = "년", example = "2021", position = 3)
    private String year;

    @ApiModelProperty(notes = "월", example = "07", position = 4)
    private String month;

    @ApiModelProperty(notes = "순번(해당 년원의 몇 번째 콘텐츠 인지)", example = "1", position = 5)
    private int seq;

    @ApiModelProperty(notes = "콘텐츠 ID", example = "9", position = 6)
    private int contentsId;

    @ApiModelProperty(notes = "활성화 여부", example = "Y", position = 7)
    private String activeOrnot;

    @ApiModelProperty(notes = "등록 ID", example = "1", position = 8)
    private int regId;

    @ApiModelProperty(notes = "등록 날짜", example = "", position = 9)
    private String regDate;

    @ApiModelProperty(notes = "수정 ID", example = "1", position = 10)
    private int modifyId;

    @ApiModelProperty(notes = "수정 날짜", example = "", position = 11)
    private String modifyDate;

    @ApiModelProperty(notes = "책 ID")
    private ResStudyBookVO bookId;

    @ApiModelProperty(notes = "책 정보")
    private ResStudyBookVO book;
}
