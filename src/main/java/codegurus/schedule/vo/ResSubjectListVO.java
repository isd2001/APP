package codegurus.schedule.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 온라인 과목 목록 조회 응답 VO
 */
@Getter
@Setter
public class ResSubjectListVO extends ResBaseVO {

    private List<ResSubjectListElemVO> list;
}
