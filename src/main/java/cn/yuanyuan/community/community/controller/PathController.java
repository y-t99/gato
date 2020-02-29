package cn.yuanyuan.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuanyuan
 * #create 2020-02-23-10:53
 */
@Controller
public class PathController {
    /**
     * 文章发布页映射
     * @return
     */
    @RequestMapping({"/public"})
    public String public_(){
        return "public";
    }

    /**
     * 文章登录页
     * @return
     */
    @GetMapping("/user/login")
    public String login(){
        return "login";
    }

    @GetMapping("/room")
    public String room(){return "room";}

}
