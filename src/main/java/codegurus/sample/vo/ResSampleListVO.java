package codegurus.sample.vo;

import codegurus.cmm.vo.res.ResListBaseVO;
import codegurus.sample.vo.SampleVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResSampleListVO extends ResListBaseVO {

    private List<SampleVO> list;
}
