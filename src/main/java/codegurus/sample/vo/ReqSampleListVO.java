package codegurus.sample.vo;

import codegurus.cmm.vo.req.ReqListBaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * 목록조회 파라미터 VO 샘플코드
 *
 *  - TODO: 굳이 파라미터 VO를 목록조회/일반 으로 분류할 필요가 있을까?
 */
@Getter
@Setter
public class ReqSampleListVO extends ReqListBaseVO {

    @ApiModelProperty(notes = "파라미터 1", required = true, example = "ABCDEF")
//    @NotBlank // 필수값 필드일 경우
//    @Email // 이메일주소 필드일 경우
//    @Min(3) // 숫자일 경우 최소값 한계
//    @Max(100) // 숫자일 경우 최대값 한계
//    @Size(min=2, max=10) // 글자길이 제약 (한글의 길이도 영문/숫자와 마찬가지로 1로 취급)
//    @Pattern(regexp="[a-zA-Z]*") // 정규식 제약
    private String param1;
}
