package codegurus.learning.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 VO
 */
@Getter
@Setter
public class CateVO {
    @ApiModelProperty(notes = "카테고리 ID")
    private String cateId;

    @ApiModelProperty(notes = "부모 ID")
    private String parentsId;

    @ApiModelProperty(notes = "깊이")
    private String depth;

    @ApiModelProperty(notes = "순서")
    private String order;

    @ApiModelProperty(notes = "카테고리 명")
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
    private String firstCateId;

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
    private String eightCateId;

    @ApiModelProperty(notes = "9차 카테고리 ID")
    private String ninthCateId;

    @ApiModelProperty(notes = "10차 카테고리 ID")
    private String tenthCateId;
}
