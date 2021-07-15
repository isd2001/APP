package codegurus.study;

import codegurus.cmm.service.FileService;
import codegurus.cmm.util.SystemUtil;
import codegurus.study.vo.ReqScoreSaveVO;
import codegurus.study.vo.ReqStudyBookVO;
import codegurus.study.vo.ResScoreSaveVO;
import codegurus.study.vo.ResStudyBookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  학습 서비스
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Service
public class StudyService {

    @Autowired
    private FileService fileService;
    @Autowired
    private StudyDAO studyDAO;

    /**
     * 오늘의 학습 책 조회
     *
     * @param reqVo
     * @return
     */
    public ResStudyBookVO selectBookDetail(ReqStudyBookVO reqVo) {

        ResStudyBookVO resVo = studyDAO.selectBookDetail(reqVo);
        if(resVo == null){ SystemUtil.returnNoSearchResult(); }  // 조회 결과 없음 리턴

        // 책 첨부파일 정보 조회/셋팅
        resVo.setBookThumbnail(fileService.selectFileVo(resVo.getBookThumbnailFileId()));

        // 책 라벨 정보 조회/셋팅
        String objectManageId = resVo.getBookId(); //대상관리 ID = 책 ID
        resVo.setLabelList(studyDAO.selectLabelList(objectManageId));

        // 책 한줄평 정보 조회/셋팅
        resVo.setOnelineList(studyDAO.selectOnelineList(resVo.getBookId()));
        return resVo;
    }


}
