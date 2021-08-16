package codegurus.schedule.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 온라인 과목 목록 조회 VO
 */
@Getter
@Setter
public class ReqSubjectListVO extends ReqBaseVO {

    // 구버전
//    @ApiModelProperty(notes = "서비스코드 PRC:스마트독서, P2.0:플라톤2.0", example ="PRC")
//    private String serviceCode;

    // 신버전
    @ApiModelProperty(notes = "1:스마트독서, 2:플라톤2.0", example ="PRC")
    private String productId;

}
