package cn.yuanyuan.community.community.controller;

import cn.yuanyuan.community.community.exception.GitHubException;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.po.GitHubUser;
import cn.yuanyuan.community.community.service.UserService;
import cn.yuanyuan.community.community.util.PoUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yuanyuan
 * #create 2020-02-20-17:24
 */
@Controller
public class AuthorizeController {
    public static final String LOGIN_MESSAGE = "login_message";
    public static final String GITHUB_LOGIN_MESSAGE = "github_login_message";
    @Autowired
    UserService userService;

    @RequestMapping("/oauth/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response,
                           HttpSession session) {
        //1、获取github用户信息
        final GitHubUser gitHubUser;
        try {
            gitHubUser = userService.getGitHubUser(code, state);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute(GITHUB_LOGIN_MESSAGE,"github用户登录功能暂时不可用");
            return "redirect:/index";
        }
        //2、更新用户
        GatoUser user = PoUtils.gitHubUserToGatoUser(gitHubUser);
        userService.updateUser(user);
        _login(response, session, user);
        return "redirect:/index";
    }

    /**
     * 用户登录后的扫尾工作
     * @param response
     * @param session
     * @param user
     */
    private void _login(HttpServletResponse response, HttpSession session, GatoUser user) {
        //3、设置cookie
        final Cookie cookie = new Cookie("token", user.getUserToken());
        cookie.setPath("/");
        response.addCookie(cookie);
        session.setAttribute("user", user);
    }

    @PostMapping("/user/login")
    public String login(@RequestParam("account") Long id, @RequestParam("password") String password,
                        HttpServletResponse response,
                        HttpSession session){
        try {
            final GatoUser user = userService.loginEAD(id, password);
            if (user==null) {
                session.setAttribute(LOGIN_MESSAGE, "账号密码错误");
            }else{
                _login(response, session, user);
                return "redirect:/index";
            }
        } catch (Exception e) {
            session.setAttribute(LOGIN_MESSAGE,"登录失败");
            e.printStackTrace();
        }
        return "login";
    }

    @GetMapping("/user/quit")
    public String quit(HttpServletResponse response, HttpSession session){
        session.removeAttribute("user");
        final Cookie cookie= new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/index";
    }
}
