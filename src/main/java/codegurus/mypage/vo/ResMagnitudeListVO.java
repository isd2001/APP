package codegurus.mypage.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.schedule.vo.ResScheduleVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 나의 진도 목록 조회 응답 VO
 */
@Getter
@Setter
public class ResMagnitudeListVO extends ResBaseVO {

    private List<ResScheduleVO> list;
}
