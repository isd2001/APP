package codegurus.auth.vo;

import codegurus.cmm.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SMS 인증의 위변조/오용 방지를 위해 서버에서 생성해 주는 토큰 생성을 위한 객체
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleIntervalVO {

    private String userManageId;
    private String productId;
    private int value;

}
