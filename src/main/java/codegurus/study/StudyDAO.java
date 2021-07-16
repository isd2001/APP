package codegurus.study;

import codegurus.study.vo.ResOnelineListElemVO;
import codegurus.study.vo.*;

import java.util.List;

/**
 * 학습 DAO
 *
 * @author 이미란
 * @version 2021.07
 */
public interface StudyDAO {

    ResStudyBookVO selectBookDetail(ReqStudyBookVO reqVo);
    List<ResLabelListElemVO> selectLabelList(String labelDomainManageId);
    List<ResOnelineListElemVO> selectOnelineList(String bookId);
}