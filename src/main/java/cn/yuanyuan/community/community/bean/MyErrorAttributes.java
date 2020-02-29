package cn.yuanyuan.community.community.bean;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


/**
 * @author yuanyuan
 * #create 2020-02-26-13:06
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        final Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);
        attributes.put("motto","今天，也是充满希望的一天");
        attributes.put("result",webRequest.getAttribute("result", RequestAttributes.SCOPE_REQUEST));
        return attributes;
    }
}
