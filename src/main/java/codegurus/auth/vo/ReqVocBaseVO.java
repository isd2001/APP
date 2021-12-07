package codegurus.auth.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.validation.DateCheck;
import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * VOC 호출 req vo의 부모
 */
@Getter
@Setter
public class ReqVocBaseVO extends ReqBaseVO {

    @ApiModelProperty(notes = "이름", required = true, example = "이지동", position = 1)
    @NotBlank
    @Size(min = 2, max = 20)
    protected String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20140511", position = 2)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    protected String birth;

    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 3)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_GENDER)
    protected String gender;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "박주미", position = 4)
    @NotBlank
    @Size(min = 2, max = 20)
    protected String parentName;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01056781234", position = 6)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    protected String parentCellphone;

    @ApiModelProperty(notes = "우편번호", required = true, example = "05869", position = 7)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_ZIPCODE)
    protected String zipcode;

    @ApiModelProperty(notes = "주소", required = true, example = "서울특별시 강동구 진황도로 189(둔촌동)", position = 8)
    @NotBlank
    @Size(min = 0, max = 200)
    protected String address;

    @ApiModelProperty(notes = "상세주소", required = true, example = "101동 1405호", position = 9)
    @NotBlank
    @Size(min = 0, max = 200)
    protected String addressDetail;

    /**
     * type
     *
     *  A: 자녀교육컨설팅인 경우
     *  C: 모의수업 신청
     */
    @JsonIgnore
    protected String type = "C";

    // 제품번호
    @JsonIgnore
    protected String prodId1 = "D010";

    // 우편번호시퀀스
    @JsonIgnore
    protected String zipcodeSq = Constants.우편번호시퀀스_기본값;

    // VOC 프로시저 호출결과
    @JsonIgnore
    protected String vocRsltCode;
    @JsonIgnore
    protected String vocRsltMsg;
}
