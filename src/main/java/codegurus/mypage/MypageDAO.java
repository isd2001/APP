package codegurus.mypage;

import codegurus.learning.vo.BookVO;
import codegurus.mypage.vo.*;
import codegurus.schedule.vo.ResScheduleVO;

import java.util.List;
import java.util.Map;

/**
 * 마이페이지 DAO
 *
 * @author 이미란
 * @version 2021.08
 */
public interface MypageDAO {

    List<ResScheduleVO> selectmagnitudeList(ReqMagnitudeListVO reqVo);
    List<BookVO> selectBookcaseList(ReqBookcaseListVO reqVo);
    List<BookVO> selectportfolioList(ReqPortfolioListVO reqVo);
    List<MyDicVO> selectDicList(ReqDicListVO reqVo);
}
