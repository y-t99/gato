package cn.yuanyuan.community.community.controller;

import cn.yuanyuan.community.community.dto.GatoCommentDTO;
import cn.yuanyuan.community.community.dto.PageDTO;
import cn.yuanyuan.community.community.dto.QuestionDTO;
import cn.yuanyuan.community.community.dto.ResultDTO;
import cn.yuanyuan.community.community.mapper.GatoCommentMapper;
import cn.yuanyuan.community.community.po.GatoArticle;
import cn.yuanyuan.community.community.po.GatoComment;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.service.ArticleService;
import cn.yuanyuan.community.community.service.UserService;
import com.alibaba.fastjson.JSON;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author yuanyuan
 * #create 2020-02-23-10:50
 */
@Controller
public class ArticleController {

    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;


    /**
     * 文章保存
     *
     * @param article 文章
     * @param session 会话
     * @return 文章页面
     */
    @PostMapping("/article")
    public String publishArticle(GatoArticle article, HttpSession session) {
        Date date = new Date(System.currentTimeMillis());
        article.setArticleGmtCreate(date);
        article.setArticleGmtModified(date);
        GatoUser user = (GatoUser) session.getAttribute("user");
        article.setArticleAuthor(user.getUserId());
        articleService.addArticle(article);
        return "public";
    }

    /**
     * 更新文章
     *
     * @param gatoArticle 文章
     * @return 文章修改回显地址
     */
    @PutMapping("/article")
    public String update(GatoArticle gatoArticle) {
        articleService.modifyArticle(gatoArticle);
        return "redirect:/public/" + gatoArticle.getArticleId();
    }
    /**
     * 文章
     *
     * @param articleId 文章id
     * @param model 模型
     * @return 文章页
     */
    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") Long articleId,
                          HttpServletRequest request, Model model) {
        articleService.increViewCount(new Pair<>(request.getRemoteAddr(), articleId));
        final QuestionDTO questionDTO = articleService.getQuestionDTOById(articleId);
        model.addAttribute("question", questionDTO);
        return "article";
    }

    /**
     * 提交评论
     * @param gatoComment 评论
     * @return 响应
     */
    @PostMapping("/article/comment")
    @ResponseBody
    public String comment(@RequestBody@Valid GatoComment gatoComment){
        Date date=new Date(System.currentTimeMillis());
        gatoComment.setCommentGmtCreateTime(date);
        gatoComment.setCommentGmtModifiedTime(date);
        articleService.addComment(gatoComment);
        return JSON.toJSONString(ResultDTO.okOf());
    }
    /**
     * 评论查看
     *
     * @param articleId 文章ID
     * @param rows 行数
     * @param currentPage 当前页
     * @param model 模型
     * @return 返回页
     */
    @GetMapping("/article/comment/{id}/{rows}/{currentPage}")
    @ResponseBody
    public Object comment(@PathVariable("id") Long articleId,@PathVariable("rows") Integer rows,
                          @PathVariable("currentPage") Integer currentPage, Model model) {
        PageDTO<GatoCommentDTO> pageDTO=new PageDTO<>();
        pageDTO.setRows(rows);
        pageDTO.setCurrentPage(currentPage);
        pageDTO.setPath("/article/comment/"+articleId);
        articleService.getCommentPage(pageDTO,articleId);
        return pageDTO;
    }

    /**
     * 首页问题分页
     *
     * @param rows 行数
     * @param currentPage 当前页
     * @param model 模型
     * @return 返回页
     */
    @GetMapping("/page/{rows}/{currentPage}")
    public String page(@PathVariable("rows") Integer rows, @PathVariable("currentPage") Integer currentPage
            , Model model) {
        PageDTO<QuestionDTO> page = new PageDTO<>();
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        page.setPath("page");
        articleService.getQuestionsPage(page);
        model.addAttribute("page", page);
        return "index";
    }

    /**
     * 文章修改回显
     *
     * @param articleId 文章id
     * @param model 模型
     * @return 发布页
     */
    @GetMapping("/public/{id}")
    public String articleEdit(@PathVariable("id") Long articleId,
                              Model model) {
        final QuestionDTO questionDTO = articleService.getQuestionDTOById(articleId);
        model.addAttribute("question", questionDTO);
        return "public";
    }

}
