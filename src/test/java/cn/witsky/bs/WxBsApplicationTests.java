package cn.witsky.bs;

import cn.witsky.bs.exception.WxBsServiceException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxBsApplicationTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String REQUEST_TYPE_POST = "POST";
    private static final String REQUEST_TYPE_GET = "GET";
    private static final String REQUEST_TYPE_PUT = "PUT";
    private static final String REQUEST_TYPE_DELETE = "DELETE";


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private String url = null;

    private String requestBody = null;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    private MvcResult getMvcResult(String requestType, String url, String requestBody, HttpHeaders httpHeaders) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder;
        if (WxBsApplicationTests.REQUEST_TYPE_POST.equals(requestType)) {
            mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(url);
        } else if (WxBsApplicationTests.REQUEST_TYPE_GET.equals(requestType)) {
            mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(url);
        } else if (WxBsApplicationTests.REQUEST_TYPE_PUT.equals(requestType)) {
            mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(url);
        } else if (WxBsApplicationTests.REQUEST_TYPE_DELETE.equals(requestType)) {
            mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete(url);
        } else {
            throw new WxBsServiceException("未知的requestType: " + requestType);
        }
        return mockMvc.perform(mockHttpServletRequestBuilder.
                contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .headers(httpHeaders)
                .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    protected void doPost() throws Exception {
        doPost(null);
    }

    protected void doGet() throws Exception {
        print(getMvcResult(WxBsApplicationTests.REQUEST_TYPE_GET, getUrl(), getRequestBody(), getHttpHeaders(null)));
    }


    protected void doGet(Map<String, String> map) throws Exception {
        print(getMvcResult(WxBsApplicationTests.REQUEST_TYPE_GET, getUrl(), getRequestBody(), getHttpHeaders(map)));
    }

    protected void doPut() throws Exception {
        print(getMvcResult(WxBsApplicationTests.REQUEST_TYPE_PUT, getUrl(), getRequestBody(), getHttpHeaders(null)));
    }

    protected void doDelete() throws Exception {
        print(getMvcResult(WxBsApplicationTests.REQUEST_TYPE_DELETE, getUrl(), getRequestBody(), getHttpHeaders(null)));
    }

    protected void doPost(Map<String, String> headers) throws Exception {
        print(getMvcResult(WxBsApplicationTests.REQUEST_TYPE_POST, getUrl(), getRequestBody(), getHttpHeaders(headers)));
    }

    private void print(MvcResult result) throws UnsupportedEncodingException {
        System.out.println("-------------------------------------");
        System.out.println("result: " + result.getResponse().getContentAsString());
    }

    private HttpHeaders getHttpHeaders(Map<String, String> map) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (map == null || map.size() == 0) {
            return httpHeaders;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        return httpHeaders;
    }

    protected String getCurrentDate() {
        DateTimeFormatter formattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime current = LocalDateTime.now();
        return current.format(formattern);
    }

}
