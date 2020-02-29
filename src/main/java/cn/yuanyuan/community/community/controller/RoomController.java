package cn.yuanyuan.community.community.controller;

import cn.yuanyuan.community.community.dto.PageDTO;
import cn.yuanyuan.community.community.dto.QuestionDTO;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.service.ArticleService;
import cn.yuanyuan.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * @author yuanyuan
 * #create 2020-02-25-10:24
 */
@Controller
public class RoomController {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/room/publish/{rows}/{currentPage}")
    public String publish(@PathVariable("rows") Integer rows, @PathVariable("currentPage") Integer currentPage
            , Model model, HttpSession session) {
        PageDTO<QuestionDTO> page = new PageDTO<>();
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        page.setPath("room/publish");
        final GatoUser user = (GatoUser) session.getAttribute("user");
        articleService.getQuestionsPageByAuthor(page, user.getUserId());
        model.addAttribute("page", page);
        model.addAttribute("room_type", "publish");
        model.addAttribute("room_type_name", "我的发布");
        return "room";
    }

    @GetMapping("/room/publish")
    public String publish() {
        return "forward:/room/publish/10/1";
    }
}
