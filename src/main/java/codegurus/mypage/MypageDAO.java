package codegurus.mypage;

import codegurus.auth.vo.ReqUpdatePWVO;
import codegurus.mypage.vo.ReqPromotionAgreeVO;
import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.ContentsHistoryVO;
import codegurus.mypage.vo.*;

import java.util.List;
import java.util.Map;

/**
 * 마이페이지 DAO
 *
 * @author 이미란
 * @version 2021.08
 */
public interface MypageDAO {

    List<ContentsHistoryVO> selectmagnitudeList(ReqMagnitudeListVO reqVo);
    List<BookVO> selectBookcaseList(ReqBookcaseListVO reqVo);
    List<BookVO> selectportfolioList(ReqPortfolioListVO reqVo);
    List<ContentsHistoryVO> selectDicList(ReqDicListVO reqVo);
    Map<String, String> selectParentInfo(ReqUserInfoVO reqVo);
    int updateUserInfo(ReqUserUpdateVO reqVo);
    int updateUserPassword(ReqUpdatePWVO reqVo);
    int deleteUser(String userManageId);

    int updatePromotionAgree(ReqPromotionAgreeVO reqVo);

    int updateAppPushAgree(ReqAppPushAgreeVO reqVo);
    int updateSoundtrackPlay(ReqSoundtrackPlayUpdateVO reqVo);
    int updateGuideActivate(ReqGuideActivateUpdateVO reqVo);
    int updateThemeMode(ReqThemeModeUpdateVO reqVo);
}
