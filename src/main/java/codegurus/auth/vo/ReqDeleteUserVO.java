package codegurus.auth.vo;

import codegurus.cmm.vo.req.ReqBaseVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정 삭제 요청 VO
 */
@Getter
@Setter
public class ReqDeleteUserVO extends ReqBaseVO {

    @JsonIgnore
    private String userManageId;
}
