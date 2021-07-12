package codegurus.sample.vo;

import codegurus.cmm.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * VO 샘플
 */
@Getter
@Setter
public class SampleVO extends BaseVO {

    private String name;
    private int age;
}
