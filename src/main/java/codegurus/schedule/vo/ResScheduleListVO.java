package codegurus.schedule.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 도서일정 목록 조회 VO
 */
@Getter
@Setter
public class ResScheduleListVO extends ResBaseVO {

    private List<ResScheduleVO> list;
}
