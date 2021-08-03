package codegurus.oneline;

import codegurus.cmm.constants.ResCodeEnum;
import codegurus.cmm.exception.CustomException;
import codegurus.cmm.util.StringUtil;
import codegurus.cmm.util.SystemUtil;
import codegurus.oneline.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  한줄평/좋아요/별점 서비스
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Service
public class OnelineService {
    @Autowired
    private OnelineDAO onelineDAO;


    /**
     * 오늘의 학습 책 한줄평 목록 조회
     *
     * @param reqVo
     * @return
     */
    public ResOnelineListVO selectOnelineList(ReqOnelineListVO reqVo) {

        ResOnelineListVO resVo = new ResOnelineListVO();
        resVo.setList(onelineDAO.selectOnelineList(reqVo));

        return resVo;

    }

    /**
     * 오늘의 학습 책 한줄평/별점 등록
     *
     * @param reqVo
     * @param resVo
     * @return
     */
    public void saveOneline(ReqOnelineSaveVO reqVo, ResOnelineSaveVO resVo) {

        /* 회원 ID 하나에 한줄평 테이블에 한줄평 내용, 별점은 한번만 등록 할 수 있어서 등록되어 있는 한줄평 내용 또는 별점이 있는지 확인 */
        ResOnelineVO resOnelineVo = onelineDAO.selectOneline(reqVo);

        // 등록 유형이 한줄평 내용일 경우
        if(reqVo.getSaveType().equals("C")) {
            if(StringUtil.isNotBlank(resOnelineVo.getOnelinereviewContent())) { // 한줄평 내용이 등록되어 있으면
                throw new CustomException(ResCodeEnum.INFO_1000.name(), "이미 한줄평을 등록하셨습니다.");
            } else if(resOnelineVo == null) { // 별점, 한줄평 내용 모두 등록되어 있지 않으면 별점 없이 한줄평 내용만 등록
                onelineDAO.insertOnelineContent(reqVo);
                resVo.setResMsg("한줄평 등록 완료");
            } else { // 별점은 등록되어 있고 한줄평 내용이 등록되어 있지 않으면 한줄평 내용만 업데이트
                int updated = onelineDAO.updateOnelineContent(reqVo);
                SystemUtil.checkUpdatedCount(updated, 1);
                resVo.setResMsg("한줄평 등록 완료");
            }
        // 등록 유형이 별점일 경우
        } else if(reqVo.getSaveType().equals("S")){
            if(StringUtil.isNotBlank(resOnelineVo.getScore())) { // 별점이 등록되어 있으면
                throw new CustomException(ResCodeEnum.INFO_1000.name(), "이미 별점을 등록하셨습니다.");
            } else if(resOnelineVo == null) { // 별점, 한줄평 내용 모두 등록되어 있지 않으면 한줄평 내용 없이 별점만 등록
                onelineDAO.insertOnelineScore(reqVo);
                resVo.setResMsg("한줄평 등록 완료");
            } else { // 한줄평 내용은 등록되어 있고 별점이 등록되어 있지 않으면 별점만 업데이트
                int updated = onelineDAO.updateOnelineScore(reqVo);
                SystemUtil.checkUpdatedCount(updated, 1);
                resVo.setResMsg("한줄평 등록 완료");
            }
        }


        // 응답에 한줄평 ID 바인딩
        resVo.setOnelinereviewId(reqVo.getOnelinereviewId());
    }

    /**
     * 한줄평 좋아요 추가/제거
     *
     * @param reqVo
     * @param resVo
     * @return
     */
    public ResOnelineMarkGoodVO saveMarkGood(ReqOnelineMarkGoodVO reqVo, ResOnelineMarkGoodVO resVo) {
        //해당 한줄평이 등록되어 있는지 조회
        boolean existOneline = onelineDAO.selectOnelineCount(reqVo) > 0 ? true : false;
        if(!existOneline) {
            throw new CustomException(ResCodeEnum.WARN_1000.name(), "해당 한줄평 onelinereviewId:["+ reqVo.getOnelinereviewId() +"] 이 존재하지 않습니다.");
        }

        //해당 한줄평에 해당 사용자가 좋아요를 했는지 조회
        boolean existLike = onelineDAO.selectMarkGoodCount(reqVo) > 0 ? true : false;

        if(reqVo.getCmd().equals("A")) {
            if(existLike){
                throw new CustomException(ResCodeEnum.INFO_1000.name(), "이미 좋아요를 하셨습니다.");
            }
            onelineDAO.insertMarkGood(reqVo);
            resVo.setResMsg("한줄평 좋아요 추가 완료");
        } else {
            if(! existLike){
                throw new CustomException(ResCodeEnum.WARN_1000.name(), "제거할 한줄평 좋아요가 존재하지 않습니다.");
            }

            int updated = onelineDAO.deleteMarkGood(reqVo);
            SystemUtil.checkUpdatedCount(updated, 1);
            resVo.setResMsg("한줄평 좋아요 제거 완료");
        }

        // 응답에 요청값 바인딩
        resVo.setRegId(reqVo.getRegId());
        resVo.setCmd(reqVo.getCmd());
        resVo.setOnelinereviewId(reqVo.getOnelinereviewId());
        resVo.setOnelinereviewLikeId(reqVo.getOnelinereviewId());

        return resVo;
    }
}
