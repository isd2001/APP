package codegurus.learning.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 라벨 VO
 */
@Getter
@Setter
public class LabelVO {


    @JsonIgnore
    private String bookId;

    @ApiModelProperty(notes = "라벨 관리 ID")
    private String labelManageId;

    @ApiModelProperty(notes = "라벨 영역 관리 id")
    private String labelDomainManageId;

    @ApiModelProperty(notes = "라벨 명")
    private String labelTitle;

    @ApiModelProperty(notes = "라벨 색깔")
    private String labelColor;
}
