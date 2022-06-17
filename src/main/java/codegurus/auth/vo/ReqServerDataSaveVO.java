package codegurus.auth.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * 서버 데이터 저장 요청 VO
 */
@Getter
@Setter
public class ReqServerDataSaveVO extends ReqBaseVO {

    @ApiModelProperty(notes = "키", example = "키")
    @NotBlank
    private String key;

    @ApiModelProperty(notes = "값", example = "값")
    private String value;

    @JsonIgnore
    private String userManageId;
}
