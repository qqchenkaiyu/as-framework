package cn.witsky.bs.ndfjr.communication;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author jwpeng
 * @time 2018/2/1 11:31
 */
@Data
@NoArgsConstructor
@ToString
public class OnlineSmsData {
    String requestId;

    String telB;

    String smscontent;

    String statusreport;
}
