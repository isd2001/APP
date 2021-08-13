package codegurus.mypage.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.BookVO;
import codegurus.schedule.vo.ResScheduleVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 나의 포트폴리오 목록 응답 VO
 */
@Getter
@Setter
public class ResPortfolioListVO extends ResBaseVO {

    private List<BookVO> list;
}
