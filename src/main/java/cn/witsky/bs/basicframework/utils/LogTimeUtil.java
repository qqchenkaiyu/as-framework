package cn.witsky.bs.basicframework.utils;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Administrator on 2019/4/12.
 */
public class LogTimeUtil {
	static {
		yyyyMMddHHmmssSSSUTC = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS").withZoneUTC();
		yyyyMMddHHmmssUTC = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC();
		yyyyMMddHHmmssSSS = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
		yyyyMMddHHmmss = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	}
	public static DateTimeFormatter yyyyMMddHHmmssSSS;
	public static DateTimeFormatter yyyyMMddHHmmss;
	public static DateTimeFormatter yyyyMMddHHmmssSSSUTC;
	public static DateTimeFormatter yyyyMMddHHmmssUTC;
	//获得格林威治时间  UTC  北京时间是UBT+8  东八区
	public static String getTime() {
		return yyyyMMddHHmmssSSS.print(System.currentTimeMillis());
	}
	public static String parseTime(Long time) {
		if(time==null){return  null;}
		return yyyyMMddHHmmssSSS.print(time);
	}
	public static String parseTimeWithColon(Long time) {
		if(time==null){return  null;}
		return yyyyMMddHHmmss.print(time);
	}

	public static void main(String[] args) {
		String s = parseTime(System.currentTimeMillis());
		System.out.println(s);
	}
}
