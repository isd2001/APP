package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 정회원 인증 전 계약정보 조회 응답 VO
 */
@Getter
@Setter
public class ResContractInfoElemVO extends ResBaseVO {

    @ApiModelProperty(notes = "계약자", example = "안유나", position = 1)
    private String parentName;

    @ApiModelProperty(notes = "자녀 이름", example = "김승주", position = 2)
    private String name;

    @ApiModelProperty(notes = "과목 명", example = "", position = 3) // TODO: 과목 명인지 상품 명인지, 오프라인/온라인 여부 확인
    private String sapSubjectTitle;

    @ApiModelProperty(notes = "상태", example = "", position = 4) // TODO: 어떤 값을 뿌려줄 것인지 확인
    private String eduStatCdNm;
}
