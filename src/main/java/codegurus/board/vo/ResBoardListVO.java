package codegurus.board.vo;

import codegurus.cmm.vo.res.ResBaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 게시판 목록 조회 응답 VO
 */
@Getter
@Setter
public class ResBoardListVO extends ResBaseVO {

    private List<ResBoardListElemVO> list;
}
