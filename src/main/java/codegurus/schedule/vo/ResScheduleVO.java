package codegurus.schedule.vo;

import codegurus.cmm.vo.BaseVO;
import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.ContentsHistoryVO;
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

    @ApiModelProperty(notes = "위치 (현재는 1~8의 값으로 위치별 번호가 있음)", example = "1", position = 8)
    private String position;

    @ApiModelProperty(notes = "등록 ID", example = "1", position = 9)
    private int regId;

    @ApiModelProperty(notes = "등록 날짜", example = "", position = 10)
    private String regDate;

    @ApiModelProperty(notes = "수정 ID", example = "1", position = 11)
    private int modifyId;

    @ApiModelProperty(notes = "수정 날짜", example = "", position = 12)
    private String modifyDate;

    @ApiModelProperty(notes = "책 정보")
    private BookVO book;

    @ApiModelProperty(notes = "콘텐츠 이력 정보")
    private ContentsHistoryVO contentsHistory;
}
