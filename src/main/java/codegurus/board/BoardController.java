package codegurus.board;

import codegurus.board.vo.ReqBoardDetailVO;
import codegurus.board.vo.ReqBoardListVO;
import codegurus.board.vo.ResBoardDetailVO;
import codegurus.board.vo.ResBoardListVO;
import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 게시판 controller
 *
 * @author 이미란
 * @version 2021.07
 */
@Slf4j
@Api( tags = "게시판")
@RequestMapping("/board")
@RestController
public class BoardController extends BaseController {

    @Autowired
    private BoardService boardService;

    /**
     * 공지사항 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/noticeList")
    @ApiOperation(value = "공지사항 목록 조회")
    public Res<ResBoardListVO> noticeList (@RequestBody @Valid ReqBoardListVO reqVo) {

        reqVo.setType("공지사항");
        ResBoardListVO resVo = boardService.selectBoardList(reqVo);
        return new Res<ResBoardListVO>(resVo);
    }

    /**
     * 공지사항 상세 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/noticeDetail")
    @ApiOperation(value = "공지사항 상세 조회")
    public Res<ResBoardDetailVO> noticeDetail (@RequestBody @Valid ReqBoardDetailVO reqVo) {

        reqVo.setType("공지사항");
        ResBoardDetailVO resVo = boardService.selectBoardDetail(reqVo);
        return new Res<ResBoardDetailVO>(resVo);
    }

    /**
     * FAQ 목록 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/faqList")
    @ApiOperation(value = "FAQ 목록 조회")
    public Res<ResBoardListVO> qnaList (@RequestBody @Valid ReqBoardListVO reqVo) {

        reqVo.setType("FAQ");
        ResBoardListVO resVo = boardService.selectBoardList(reqVo);
        return new Res<ResBoardListVO>(resVo);
    }

    /**
     * FAQ 상세 조회
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/faqDetail")
    @ApiOperation(value = "FAQ 상세 조회")
    public Res<ResBoardDetailVO> faqDetail (@RequestBody @Valid ReqBoardDetailVO reqVo) {

        reqVo.setType("FAQ");
        ResBoardDetailVO resVo = boardService.selectBoardDetail(reqVo);
        return new Res<ResBoardDetailVO>(resVo);
    }
}
