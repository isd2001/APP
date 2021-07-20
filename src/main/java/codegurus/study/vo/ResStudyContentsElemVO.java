package codegurus.study.vo;


import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 학습 콘텐츠 조회 응답 > 리스트 요소 VO
 */
@Getter
@Setter
public class ResStudyContentsElemVO extends ResBaseVO {

    @ApiModelProperty(notes = "콘텐츠 ID", example = "4")
    private String contentsId;

    @ApiModelProperty(notes = "책 ID", example = "4")
    private String bookID = "";

    @ApiModelProperty(notes = "템플릿 인스턴스 ID", example = "1")
    private String templateInstId;

    @ApiModelProperty(notes = "템플릿 ID", example = "1")
    private String templateId;

    @ApiModelProperty(notes = "JSON", example = "")
    private String json;

    @ApiModelProperty(notes = "순서", example = "1")
    private String order;

    @ApiModelProperty(notes = "유형", example = "어휘")
    private String type;

    @ApiModelProperty(notes = "난이도", example = "중")
    private String difficulty;

    @ApiModelProperty(notes = "카테고리 ID", example = "1")
    private String cateId;

    @ApiModelProperty(notes = "부모 ID", example = "1")
    private String parentsId;

    @ApiModelProperty(notes = "깊이", example = "")
    private String depth;

    @ApiModelProperty(notes = "카테고리 순서", example = "")
    private String cateOrder;

    @ApiModelProperty(notes = "카테고리 명", example = "")
    private String cateTitle;

    @ApiModelProperty(notes = "1차 카테고리 명")
    private String firstCateTitle;

    @ApiModelProperty(notes = "2차 카테고리 명")
    private String secondCateTitle;

    @ApiModelProperty(notes = "3차 카테고리 명")
    private String thirdCateTitle;

    @ApiModelProperty(notes = "4차 카테고리 명")
    private String fourthCateTitle;

    @ApiModelProperty(notes = "5차 카테고리 명")
    private String fifthCateTitle;

    @ApiModelProperty(notes = "6차 카테고리 명")
    private String sixthCateTitle;

    @ApiModelProperty(notes = "7차 카테고리 명")
    private String seventhCateTitle;

    @ApiModelProperty(notes = "8차 카테고리 명")
    private String eightCateTitle;

    @ApiModelProperty(notes = "9차 카테고리 명")
    private String ninthCateTitle;

    @ApiModelProperty(notes = "10차 카테고리 명")
    private String tenthCateTitle;

    @ApiModelProperty(notes = "1차 카테고리 ID")
    private String firthCateId;

    @ApiModelProperty(notes = "2차 카테고리 ID")
    private String secondCateId;

    @ApiModelProperty(notes = "3차 카테고리 ID")
    private String thirdCateId;

    @ApiModelProperty(notes = "4차 카테고리 ID")
    private String fourthCateId;

    @ApiModelProperty(notes = "5차 카테고리 ID")
    private String fifthCateId;

    @ApiModelProperty(notes = "6차 카테고리 ID")
    private String sixthCateId;

    @ApiModelProperty(notes = "7차 카테고리 ID")
    private String seventhCateId;

    @ApiModelProperty(notes = "8차 카테고리 ID")
    private String eighthCateId;

    @ApiModelProperty(notes = "9차 카테고리 ID")
    private String ninthCateId;

    @ApiModelProperty(notes = "10차 카테고리 ID")
    private String tenthCateId;

}
