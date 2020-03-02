package cn.yuanyuan.community.community.controller;

import cn.yuanyuan.community.community.po.GatoTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuanyuan
 * #create 2020-02-23-10:53
 */
@Controller
public class PathController {
    @Autowired
    GatoTag gatoTag;

    /**
     * 文章发布页映射
     * @return
     */
    @RequestMapping({"/public"})
    public String public_(Model model){
        model.addAttribute("gatoTag",gatoTag);
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
