package codegurus.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 스케줄 간격 VO
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
