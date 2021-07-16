package codegurus.oneline;

import codegurus.oneline.vo.ReqOnelineMarkGoodVO;
import codegurus.oneline.vo.ReqOnelineSaveVO;
import codegurus.oneline.vo.ResOnelineVO;

/**
 * 한줄평/좋아요/별점 DAO
 *
 * @author 이미란
 * @version 2021.07
 */
public interface OnelineDAO {
    ResOnelineVO selectOneline(ReqOnelineSaveVO reqVo);
    void insertOnelineContent(ReqOnelineSaveVO reqVo);
    int updateOnelineContent(ReqOnelineSaveVO reqVo);
    void insertOnelineScore(ReqOnelineSaveVO reqVo);
    int updateOnelineScore(ReqOnelineSaveVO reqVo);

    int selectOnelineCount(ReqOnelineMarkGoodVO reqVo);
    int selectMarkGoodCount(ReqOnelineMarkGoodVO reqVo);
    void insertMarkGood(ReqOnelineMarkGoodVO reqVo);
    int deleteMarkGood(ReqOnelineMarkGoodVO reqVo);
}
