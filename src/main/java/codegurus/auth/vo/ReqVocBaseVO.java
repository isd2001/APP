package codegurus.auth.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.validation.DateCheck;
import codegurus.cmm.vo.req.ReqBaseVO;
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
    private String name;

    @ApiModelProperty(notes = "생년월일", required = true, example = "20140511", position = 2)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String birth;

    @ApiModelProperty(notes = "성별", required = true, example = "M", position = 5)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_GENDER)
    private String gender;

    @ApiModelProperty(notes = "부모 이름", required = true, example = "박주미", position = 3)
    @NotBlank
    @Size(min = 2, max = 20)
    private String parentName;

    @ApiModelProperty(notes = "부모 핸드폰번호", required = true, example = "01056781234", position = 5)
    @Pattern(regexp= StringUtil.REGEX_CELLPHONE)
    private String parentCellphone;

    @ApiModelProperty(notes = "우편번호", required = true, example = "05869", position = 8)
    @NotBlank
    @Pattern(regexp=StringUtil.REGEX_ZIPCODE)
    private String zipcode;

    private String zipcodeSq = "00000";

    @ApiModelProperty(notes = "주소", required = true, example = "서울특별시 강동구 진황도로 189(둔촌동)", position = 9)
    @NotBlank
    @Size(min = 0, max = 200)
    private String address;

    @ApiModelProperty(notes = "상세주소", required = true, example = "101동 1405호", position = 10)
    @NotBlank
    @Size(min = 0, max = 200)
    private String addressDetail;

    @ApiModelProperty(notes = "SMS 인증 확인 성공 후 응답으로 받은 토큰", required = true, example = "7Ymv68kOyED3qdDXAlfPUt5lgqX5n1AQSpQjAjdKSI7SDcCj6%2FiW0yqLFEhFfbZBJWonV1bfXXFheGic1LDOeQ%3D%3D", position = 6)
    @NotBlank
    private String smsToken;


    /**
     * type
     *
     *  A: 자녀교육컨설팅인 경우
     *  B: 모의수업 신청
     */
    private String type = "A"; // TODO: 맞는지 확인



    private String addr1;
    private String addr2;
    private String prodId1;
    private String acptDt;

}
