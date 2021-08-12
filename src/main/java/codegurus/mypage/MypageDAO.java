package codegurus.mypage;

import codegurus.learning.vo.BookVO;
import codegurus.mypage.vo.ReqBookcaseListVO;
import codegurus.mypage.vo.ReqMagnitudeListVO;
import codegurus.schedule.vo.ResScheduleVO;

import java.util.List;

/**
 * 마이페이지 DAO
 *
 * @author 이미란
 * @version 2021.08
 */
public interface MypageDAO {

    List<ResScheduleVO> selectmagnitudeList(ReqMagnitudeListVO reqVo);
    List<BookVO> selectBookcaseList(ReqBookcaseListVO reqVo);
}
