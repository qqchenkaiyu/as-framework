package cn.witsky.bs.basicframework.Constants;

import lombok.NonNull;

/**
 * @author wangyuyang@witsky.cn
 * @date 2019/3/8 15:22
 */
public interface RedisKeys {

    interface X {
        String SMCGT = "smcgt";
        String APPKEY = "appkey";
        String SERVICETYPE = "serviceType";
        String DNTAG = "dnTag";
        String AREACODE = "areacode";
        String IMSI = "imsi";
        String STATUS = "status";

        String X_INFO = "X:INFO:";
        String X_INFO_FORMAT = "%s:"+X_INFO + "%s:%s";
        String XA_BIND = "X:BIND:";
        String XA_BIND_FORMAT = "%s:"+XA_BIND + "%s:%s";

    }

    static String createXInfoKey(@NonNull String prefix, @NonNull String number) {
        String dnSegment;
        if (number.length() > 2) {
            dnSegment = number.substring(0, 3);
        } else {
            dnSegment = number;
        }
        return String.format(X.X_INFO_FORMAT,prefix, dnSegment, number);
    }

    static String createXBindKey(@NonNull String prefix, @NonNull String number) {
        String dnSegment;
        if (number.length() > 2) {
            dnSegment = number.substring(0, 3);
        } else {
            dnSegment = number;
        }
        return String.format(X.XA_BIND_FORMAT,prefix, dnSegment, number);
    }


}
