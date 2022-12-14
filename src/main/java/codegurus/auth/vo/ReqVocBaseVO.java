package codegurus.auth.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.constants.ProductEnum;
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

    @ApiModelProperty(notes = "우편번호", required = true, example = "03182", position = 7)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_ZIPCODE)
    protected String zipcode;

    @ApiModelProperty(notes = "주소", required = true, example = "서울특별시 종로구 새문안로 91", position = 8)
    @NotBlank
    @Size(min = 0, max = 200)
    protected String address;

    @ApiModelProperty(notes = "상세주소", required = true, example = "12층 1216호", position = 9)
    @NotBlank
    @Size(min = 0, max = 200)
    protected String addressDetail;

    @ApiModelProperty(notes = "시군구", required = true, example = "서울특별시 종로구", position = 11)
    @NotBlank
    protected String sigungu;

    // 우편번호시퀀스
    @ApiModelProperty(notes = "우편번호시퀀스", example = "00044", position = 12)
    protected String zipcodeSq = "";

    @ApiModelProperty(notes = "상품 ID (스마트독서 : 1, 플라톤 : 2)", example = "1", position = 13)
    private String productId = ProductEnum.상품_스마트독서.getProductId();

    /**
     * type
     *
     *  A: 자녀교육컨설팅인 경우
     *  C: 모의수업 신청
     */
    @JsonIgnore
    protected String type = "C";

    // VOC 프로시저 호출결과
    @JsonIgnore
    protected String vocRsltCode;
    @JsonIgnore
    protected String vocRsltMsg;
}
