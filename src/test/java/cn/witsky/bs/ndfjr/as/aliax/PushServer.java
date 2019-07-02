package cn.witsky.bs.ndfjr.as.aliax;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@RestController

public class PushServer {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpServletRequest httpServletRequest;
    String urlPrefix="http://192.168.110.94:6051";
    @RequestMapping(value = "/push")
    public Object push(@RequestBody JSONObject object) throws Exception{
        System.out.println("模拟接收到push消息——————————————="+object);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        return jsonObject;
    }

}
