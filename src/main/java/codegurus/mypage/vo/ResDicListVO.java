package codegurus.mypage.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.BookVO;
import codegurus.learning.vo.ContentsHistoryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 나의 사전 목록 응답 VO
 */
@Getter
@Setter
public class ResDicListVO extends ResBaseVO {

    private List<ContentsHistoryVO> list;
}
