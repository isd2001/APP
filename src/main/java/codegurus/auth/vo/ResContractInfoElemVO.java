package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ApiModelProperty(notes = "과목 명", example = "", position = 3) // 기획이 좀 헷갈리지만 주석을 따르도록 하자
    private String sapSubjectTitle;

    @ApiModelProperty(notes = "상태", example = "", position = 4) // 교육상태를 출력해 주는 것이라 한다.
    private String eduStatCdNm;

    @JsonIgnore
    private String intgEduCntrId;

    @JsonIgnore
    private String intgCustId;

    @JsonIgnore
    private String intgParentCustId;

    @JsonIgnore
    private String intgSubjId;

    @JsonIgnore
    private String eduCntrOid;

    @JsonIgnore
    private String sapCustId;

    @JsonIgnore
    private String sapSubjId;

    @JsonIgnore
    private String eduStatCd;

    @JsonIgnore
    private String intgProdtId;/*SAP 데이터가 거의 없음 */

    @JsonIgnore
    private String prodtNm;

    @JsonIgnore
    private String productId;

    @JsonIgnore
    private String serviceTitle;

    @JsonIgnore
    private String subjectTitle;

    @JsonIgnore
    private String svcChagDate;

    @JsonIgnore
    private String userManageId;

}
