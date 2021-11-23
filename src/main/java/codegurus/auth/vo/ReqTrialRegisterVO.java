package codegurus.auth.vo;

import codegurus.cmm.constants.Constants;
import codegurus.cmm.constants.ProductEnum;
import codegurus.cmm.validation.DateCheck;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 체험회원 등록 요청 VO
 */
@Getter
@Setter
public class ReqTrialRegisterVO extends ReqVocBaseVO {

    @ApiModelProperty(notes = "부모 생년월일", required = true, example = "19801212", position = 5)
    @NotBlank
    @DateCheck(format = Constants.DF8)
    private String parentBirth;

    @JsonIgnore
    private String trialManageId;

    @JsonIgnore
    private String trialStartDate;
    @JsonIgnore
    private String trialEndDate;

    @JsonIgnore
    private String productId = ProductEnum.상품_스마트독서.getProductId();

    // 상담내용상세
    @JsonIgnore
    private String acptDt = "스마트독서 체험회원 등록";

    @ApiModelProperty(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50MDEiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjM3OTE3NzkwfQ.weAGWpxpw4BHnI-1rHBPn_KvciO9L8_UWTl_275oCuBLDNb8mBdA4pAfRve5SVJHmJytTIDRT0YerAxvoKaG3Q", notes = "체험등록시 기존 회원 토큰이 남아있다면 보내주면 기존 회원 계정에 연결된다. 리턴되는 토큰은 체험회원 토큰인데 이 경우 무시한다. (Bearer는 생략)")
    private String userToken;
}
