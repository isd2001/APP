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

    LearningVO selectBookDetail(ReqLearningBookVO reqVo);
    List<ResStudyContentsElemVO> selectContentsList(ReqStudyContentsListVO reqVo);

    void insertLearningContentsHistory(ReqLearningContentsHistorySaveVO reqVo);
    void insertTemplateInstHistory(List<TemplateInstHistorySaveVo> insertTemplateInstList);
}