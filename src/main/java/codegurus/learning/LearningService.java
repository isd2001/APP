package codegurus.learning;

import codegurus.cmm.service.FileService;
import codegurus.cmm.util.SystemUtil;
import codegurus.learning.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  학습 서비스
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Service
public class LearningService {

    @Autowired
    private FileService fileService;
    @Autowired
    private LearningDAO learningDAO;

    /**
     * 오늘의 학습 책 조회
     *
     * @param reqVo
     * @return
     */
    public ResLearningBookVO selectBookDetail(ReqLearningBookVO reqVo, ResLearningBookVO resVo) {

        BookVO item = learningDAO.selectBookDetail(reqVo);
        if(item == null){ SystemUtil.returnNoSearchResult(); }  // 조회 결과 없음 리턴

        resVo.setItem(item);

        return resVo;
    }

    /**
     * 학습 콘텐츠 조회
     *
     * @param reqVo
     * @return
     */
    public ResLearningContentsVO selectLearningContents(ReqLearningContentsVO reqVo, ResLearningContentsVO resVo) {

        ContentsVO item = learningDAO.selectLearningContents(reqVo);
        if(item == null){ SystemUtil.returnNoSearchResult(); } // 조회 결과 없음 리턴

        resVo.setItem(item);

        return resVo;
    }

    /**
     * 학습 콘텐츠 이력 저장
     *
     * @param reqVo
     * @param resVo
     * @return
     */
    public void saveLearningContentsHistory(ReqLearningContentsHistorySaveVO reqVo, ResLearningContentsHistorySaveVO resVo) {

        learningDAO.deleteOldContentsHistory(reqVo);

        learningDAO.insertLearningContentsHistory(reqVo);
        resVo.setResMsg("학습 콘텐츠 이력 저장");

        if(reqVo.getTemplateInstHistorySaveList().size() > 0) {
            List<TemplateInstHistorySaveVo> insertTemplateInstHistoryList = new ArrayList<>();

            for(int i = 0; i < reqVo.getTemplateInstHistorySaveList().size(); i++) {
                reqVo.getTemplateInstHistorySaveList().get(i).setContentsHistoryId(reqVo.getContentsHistoryId());
                reqVo.getTemplateInstHistorySaveList().get(i).setUserManageId(reqVo.getUserManageId());
                insertTemplateInstHistoryList.add(reqVo.getTemplateInstHistorySaveList().get(i));
            }

            if(insertTemplateInstHistoryList.size() > 0) {
                learningDAO.insertTemplateInstHistory(insertTemplateInstHistoryList);
                resVo.setResMsg("템플릿 인스턴스 이력 리스트 등록 완료");
            }
        }
        // 응답에 contentsId 바인딩
        resVo.setContentsHistoryId(reqVo.getContentsHistoryId());
        resVo.setOnlineSubjectScheduleId(reqVo.getOnlineSubjectScheduleId());
    }

    /**
     * 학습 결과 조회
     *
     * @param reqVo
     * @param resVO
     * @return
     */
    public ResLearningResultVO selectLearningResult(ReqLearningResultVO reqVo, ResLearningResultVO resVO) {
        //학습 결과 조회 (콘텐츠 이력 조회)
        ContentsHistoryVO item = learningDAO.selectContentsHistory(reqVo);

        if(item == null){ SystemUtil.returnNoSearchResult(); } // 조회 결과 없음 리턴

        //

        resVO.setItem(item);

        return resVO;
    }
}
