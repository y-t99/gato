package cn.yuanyuan.community.community.provider;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.ResourceUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyuan
 * #create 2020-02-18-0:09
 * 广金教务系统
 */
public class GDUFEAD {
    private static final String LOGIN_URL = "http://jwxt.gduf.edu.cn/jsxsd/xk/LoginToXk";
    private static final String CLASS_SCHEDULE_URL = "http://jwxt.gduf.edu.cn/jsxsd/xskb/xskb_list.do";
    private static final String MAIN = "http://jwxt.gduf.edu.cn/jsxsd/framework/main.jsp";

    /**
     * 登录教务系统
     *
     * @param account
     * @param password
     * @param client
     * @return 登录是否成功
     * @throws NoSuchMethodException
     * @throws ScriptException
     * @throws IOException
     */
    public static boolean loginEAD(String account, String password, CloseableHttpClient client) throws NoSuchMethodException, ScriptException, IOException {
        try {
            HttpPost post = new HttpPost(LOGIN_URL);
            //1、请求头
            _setLoginHeader(post);
            //2、请求体
            _setLoginBody(post, account, password);
            //3、请求
            CloseableHttpResponse response = client.execute(post);
            try {
                if (response.getStatusLine().getStatusCode() == 302) {
                    return true;
                }
                return false;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("教务系统请求异常");
    }

    /**
     * 获取页面内容
     *
     * @param client
     * @return 页面HTML
     * @throws IOException
     */
    private static String _getContent(CloseableHttpClient client, String path) throws IOException {

        HttpGet get = new HttpGet(path);
        get.addHeader(new BasicHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko Core/1.70.3741.400 QQBrowser/10.5.3863.400"));
        CloseableHttpResponse info = client.execute(get);

        return EntityUtils.toString(info.getEntity(), "utf-8");
    }

    public static String getMain(CloseableHttpClient client) throws IOException {
        return _getContent(client, MAIN);
    }

    public static Map<String, String> getName(CloseableHttpClient client) throws IOException {
        final String main = getMain(client);
        Document document = Jsoup.parse(main, "utf-8");
        final Elements elements = document.select(".block1text");
        final String text = elements.get(0).text();
        final String[] ss = text.trim().split(" ");
        Map<String, String> map = new HashMap<>(2);
        for (String s :
                ss) {
            final String[] split = s.split("：");
            if ("姓名".equals(split[0])) {
                map.put("name", split[1]);
            } else if ("学号".equals(split[0])) {
                map.put("id", split[1]);
            }
        }
        return map;
    }

    /**
     * 设置登录请求体
     *
     * @param post
     * @param account
     * @param password
     * @throws UnsupportedEncodingException
     * @throws NoSuchMethodException
     * @throws ScriptException
     * @throws FileNotFoundException
     */
    private static void _setLoginBody(HttpPost post, String account, String password) throws UnsupportedEncodingException, NoSuchMethodException, ScriptException, FileNotFoundException {
        String encoded = _getEncoded(account, password);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("encoded", encoded));
        StringEntity entity = new UrlEncodedFormEntity(params);
        post.setEntity(entity);
    }

    /**
     * 设置登录请求头
     *
     * @param post
     */
    private static void _setLoginHeader(HttpPost post) {
        Header header = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko Core/1.70.3741.400 QQBrowser/10.5.3863.400");
        Header from = new BasicHeader("Referer", "http://jwxt.gduf.edu.cn/jsxsd/");
        Header contentType = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addHeader(header);
        post.addHeader(from);
        post.addHeader(contentType);
    }

    /**
     * 获取登录encoded参数
     *
     * @param account
     * @param password
     * @return encoded
     * @throws ScriptException
     * @throws NoSuchMethodException
     * @throws FileNotFoundException
     */
    private static String _getEncoded(String account, String password) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        return EncodeParameter.encodeParameter(account)
                + "%%%" + EncodeParameter.encodeParameter(password);
    }

    /**
     * 内部类，对参数进行加密
     */
    private static class EncodeParameter {
        /**
         * 回调函数器
         */
        private static Invocable invocable;
        /**
         * 加密函数名
         */
        private static final String ENCODE_INP = "encodeInp";
        /**
         * 加密的js函数
         */
        private static String ENCODE_JS;

        /**
         * 创建加密函数
         */
        static {
            try {
                File file = ResourceUtils.getFile("classpath:static/js/conwork.js");
                ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
                scriptEngine.eval(new FileReader(file));
                if (scriptEngine instanceof Invocable) {
                    invocable = (Invocable) scriptEngine;
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * 参数加密
         *
         * @param parameter
         * @return 加密后的字符串
         * @throws ScriptException
         * @throws NoSuchMethodException
         * @throws FileNotFoundException
         */
        public static String encodeParameter(String parameter) throws ScriptException, NoSuchMethodException {
            if (invocable == null) {
                throw new RuntimeException("invocable为null");
            }
            return (String) invocable.invokeFunction(ENCODE_INP, parameter);
        }
    }
}
