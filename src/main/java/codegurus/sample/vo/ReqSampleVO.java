package codegurus.sample.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import codegurus.cmm.vo.req.ReqListBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqSampleVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "너부리")
    private String name;

    @ApiModelProperty(notes = "나이", required = true, example = "23")
    private int age;
}
