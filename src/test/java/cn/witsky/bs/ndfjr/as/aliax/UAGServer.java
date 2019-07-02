package cn.witsky.bs.ndfjr.as.aliax;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UAGServer {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private Environment environment;

    //    @Value("witsky.connected")
//            String connected;
    String urlPrefix="http://192.168.110.94:6051";
    @RequestMapping("/MTWM")
    public Object MTWM(@RequestBody final JSONObject object) throws Exception{
        System.out.println("模拟UAG接收到消息———serviceConnect———————————="+object);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        return jsonObject;
    }
    @RequestMapping("/MTCX")
    public Object MTCX(@RequestBody final JSONObject object) throws Exception{
        System.out.println("模拟UAG接收到消息———MTCX———————————="+object);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        return jsonObject;
    }

}
