package codegurus.sample;

import codegurus.cmm.controller.BaseController;
import codegurus.cmm.vo.res.Res;
import codegurus.sample.vo.ReqSampleListVO;
import codegurus.sample.vo.ReqSampleVO;
import codegurus.sample.vo.ResSampleListVO;
import codegurus.sample.vo.ResSampleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 개발 표준을 제시하기 위한 sample controller 입니다
 *
 *  - 참고
 *      - 이전 프로젝트에서 사용하던 controller method의 두 번째 args인 BindingResult 는 사용하지 말아야 합니다.
 *      - 유효성검사를 위해 controller method args 앞에 @Valid 를 붙입니다. (유효성검사 필드별 annotation 코딩은 ReqSampleListVO 참고)
 *      - 추가적인 유효성검사 요건이 생길 경우 CustomExceptionHandler의 switch(validAnnoName).. 하위에 추가 정의합니다.
 *      - @Api~ 로 시작하는 annotation들은 swagger에 사용자정의 값을 보여주기 위한 것들임.
 *
 * @author 이프로
 * @version 2021.06
 */
@Slf4j
@Api(tags = "샘플 컨트롤러")
@RequestMapping("/sample")
@RestController
public class SampleController extends BaseController {

    @Autowired
    private SampleService sampleService;

    @PostMapping("/list")
    @ApiOperation(value = "샘플 목록 조회")
    public Res<ResSampleListVO> list(@RequestBody @Valid ReqSampleListVO reqVo) {

        ResSampleListVO resVo = sampleService.selectTestList(reqVo);
        log.debug("## result: {}", resVo);

        // 임의로 코드 흐름을 끊고 에러메시지를 응답으로 보내는 샘플코드
        if(true){
//            throw new CustomException(ResCodeEnum.ERROR_0002); // 기 정의된 에러코드/메시지를 사용하는 경우
//            throw new CustomException(ResCodeEnum.ERROR_0002.name(), "블라블라"); // 에러코드는 기 정의된 것을 사용하되, 에러메시지는 override 하는 경우 => .name() 을 사용하는 것에 주의!!
        }

        return new Res<ResSampleListVO>(resVo);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "샘플 데이터 insert")
    public Res<ResSampleVO> insert(@RequestBody @Valid ReqSampleVO reqVo) {

        ResSampleVO resVo = new ResSampleVO();

        sampleService.insertTest(reqVo);

        return new Res<ResSampleVO>(resVo);
    }
}
