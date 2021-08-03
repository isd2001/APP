package codegurus.oneline.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 한줄평 목록 조회 응답VO
 */
@Getter
@Setter
public class ResOnelineListVO extends ResBaseVO {

    private List<OnelineVO> list;
}