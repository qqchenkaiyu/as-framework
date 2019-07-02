package cn.witsky.bs.ndfjr.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Administrator
 * @date 2017/7/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsToSlfSmsSubmitWay {

    /**
     * 发送方式
     */
    private int way;

    /**
     * 短信中心地址
     */
    private String smcGt;

    private String smcHDUI;

    private String smcStatusReport;

    public AsToSlfSmsSubmitWay(int way, String smcGt) {
        this.way = way;
        this.smcGt = smcGt;
    }
}
