package codegurus.auth.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 계약정보 조회 (정회원 인증 전) 응답 VO
 */
@Getter
@Setter
public class ResContractInfoVO extends ResBaseVO {

    private List<ResContractInfoElemVO> list;
}
