package cn.witsky.bs.basicframework.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author HuangYX
 * @date 2018/5/29 15:48
 */
public class ResultUtil {

  private static Logger logger = LoggerFactory.getLogger(ResultUtil.class);
//  /**
//   * 处理错误响应
//   *
//   * @param message
//   * @return
//   */
//
//  public static Object errControlResult(String message) {
//    JSONObject object = new JSONObject();
//    object.put(Field.CODE,"1");
//    object.put(Field.MESSAGE,message);
//    object.put(Field.SUCCESS, false);
//    return object;
//  }

  public static Map getSignHeaderMap(HttpServletRequest request, String signheader) {
    Enumeration<String> headerNames = request.getHeaderNames();
    HashMap<String, String> map = new HashMap<>();
    List<String> signheaders = Arrays.asList(signheader.split(","));
    while
    (headerNames.hasMoreElements()) {
      String key =  headerNames.nextElement();
      if(signheaders.contains(key)) {
        String value = request.getHeader(key);
        map.put(key, value);
      }
    }
    return  map;

  }
  public static String getBody(HttpServletRequest request) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String body = IOUtils.toString(reader);
return body;
  }

  public static Object signFail() {
    return Result.signFail;
  }
  public static Object Ok() {
    return Result.ok;
  }
  public static Object overTime() {return Result.overTime; }
  public static Object noResource() {return Result.noResource; }
  public static Object unExist() {return Result.unExist; }
  public static Object error(String message) {
    return new Result("1",message);
  }

  @Data
  @AllArgsConstructor
  static class Result{
    String code;
    String message;
    static Result signFail=new Result("401","请求的来源或者请求的签名未通过校验");
    static Result ok=new Result("0","success");
    static Result noResource=new Result("402","号码池资源不足");
    static Result overTime=new Result("501","业务处理超时");
    static Result unExist=new Result("403","绑定关系不存在");


  }


}
