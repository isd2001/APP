package codegurus.study.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 학습 콘텐츠 목록 웅답 VO
 */
@Getter
@Setter
public class ResStudyContentsListVO extends ResBaseVO {

    private List<ResStudyContentsElemVO> list;
}
