package cn.yuanyuan.community.community.controller;

import cn.yuanyuan.community.community.dto.PageDTO;
import cn.yuanyuan.community.community.dto.QuestionDTO;
import cn.yuanyuan.community.community.service.ArticleService;
import cn.yuanyuan.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuanyuan
 * #create 2020-02-20-17:20
 */
@Controller
public class IndexController {
    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;
    /**
     * 首页映射
     * @return 首页地址
     */
    @RequestMapping({"/","/index.html","/index"})
    public String index(Model model){
        PageDTO<QuestionDTO> page=new PageDTO<>();
        page.setCurrentPage(1);
        page.setRows(10);
        page.setPath("page");
        articleService.getQuestionsPage(page);
        model.addAttribute("page", page);
        return "index";
    }
}
