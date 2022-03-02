package codegurus.learning;

import codegurus.learning.vo.*;

import java.util.List;

/**
 * 학습 DAO
 *
 * @author 이미란
 * @version 2021.07
 */
public interface LearningDAO {

    BookVO selectBookDetail(ReqLearningBookVO reqVo);
    ContentsVO selectLearningContents(ReqLearningContentsVO reqVo);

    int deleteOldContentsHistory(ReqLearningContentsHistorySaveVO reqVo);

    void insertLearningContentsHistory(ReqLearningContentsHistorySaveVO reqVo);
    void insertTemplateInstHistory(List<TemplateInstHistorySaveVo> insertTemplateInstList);

    ContentsHistoryVO selectContentsHistory(ReqLearningResultVO reqVo);

    ClassPreparationHistoryVO selectClassPreparationHistory(ClassPreparationHistoryVO reqVo);
    void insertClassPreparationHistory(ClassPreparationHistoryVO reqVo);

    String selectNextContentsId(ReqLearningNextContentsInfoVO reqVo);

    ClassManageVO selectClassManage(ReqLearningClassCheckVO reqVo);
    void insertClassAttendHistory(ReqLearningClassCheckVO reqVo);
}