package codegurus.mypage.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import codegurus.learning.vo.BookVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 나의 책장 목록 응답 VO
 */
@Getter
@Setter
public class ResBookcaseListVO extends ResBaseVO {

    private List<BookVO> list;
}
