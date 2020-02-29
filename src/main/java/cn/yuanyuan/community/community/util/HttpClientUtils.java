package cn.yuanyuan.community.community.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyuan
 * #create 2020-02-01-10:59
 */
public class HttpClientUtils {
    /**
     * 状态码
     */
    private class Code {
        /**
         * 响应成功
         */
        static final int SUCCESS_CODE = 200;
    }

    /**
     * 请求方法
     */
    private enum Method {
        /**
         * get请求
         */
        GET,
        /**
         * post请求
         */
        POST
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 连接池
     */
    private static final PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

    private static CloseableHttpClient client;

    /*工具包初始化*/
    static {
        client= HttpClients.custom().setConnectionManager(pool).build();
    }
    public static CloseableHttpClient getClient(){
        return HttpClients.custom().setConnectionManager(pool).build();
    }

    public static String doGet(HttpGet get) throws IOException {
        return _request(get);
    }

    public static String doPost(HttpPost post) throws IOException {
        return _request(post);
    }

    public static String doPost(HttpPost post,Object parameters) throws IOException{
        String jsonString = JSON.toJSONString(parameters);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/json;charset=utf8");
        return _request(post);
    }

    private static String _request( HttpRequestBase request) throws IOException {
        CloseableHttpResponse response=null;
        request.setHeader(new BasicHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko Core/1.70.3741.400 QQBrowser/10.5.3863.400"));
        try {
            response=client.execute(request);
            return EntityUtils.toString(response.getEntity(),"utf-8");
        } finally {
            if (response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将Object对象转为List<NameValuePair>
     * @param object
     * @return post请求参数
     */
    private static List<NameValuePair> _transform(Object object){
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(object));
        List<NameValuePair> list=new ArrayList<>();
        map.forEach((key,value)->{
            list.add(new BasicNameValuePair(key, (String) value));
        });
        return list;
    }
}
