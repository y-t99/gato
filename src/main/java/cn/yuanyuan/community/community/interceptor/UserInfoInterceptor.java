package cn.yuanyuan.community.community.interceptor;

import cn.yuanyuan.community.community.mapper.GatoUserMapper;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.util.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuanyuan
 * #create 2020-02-21-23:25
 */
@Service
public class UserInfoInterceptor implements HandlerInterceptor {
    @Autowired
    GatoUserMapper gatoUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Object user = request.getSession().getAttribute("user");
        if(user==null){
            final Cookie[] cookies = request.getCookies();
            if(cookies!=null) {
                String token = null;
                for (Cookie cookie :
                        cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
                if (token != null) {
                    try {
                        final Claims claims = JjwtUtils.parseJWT(token);
                        final Long id = claims.get("userId", Long.class);
                        final GatoUser gatoUser = gatoUserMapper.selectById(id);
                        request.getSession().setAttribute("user", gatoUser);
                    } catch (Exception e) {
                        System.out.println("token过期");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
