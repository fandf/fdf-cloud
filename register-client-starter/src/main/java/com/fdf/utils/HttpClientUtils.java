package com.fdf.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author dongfengfan
 */
public class HttpClientUtils {

    private static RequestConfig requestConfig = null;
    private static final Log log = LogFactory.getLog(HttpClientUtils.class);
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int CONNECT_TIMEOUT = 5000;

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
    }

    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    log.error("请求服务器端出错：", e);
                }
            }
        } catch (IOException e) {
            log.error("请求服务器端出错：", e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        JSONObject data = new JSONObject();
        data.put("serviceName", "fdf");
        data.put("serviceAddres", "127.0.0.1:8080");
        JSONObject result = HttpClientUtils.httpPost("http://127.0.0.1:8848/register", data);
        System.out.println(result);
    }

}