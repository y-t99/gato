package cn.yuanyuan.community.community.service;

import cn.yuanyuan.community.community.dto.GatoCommentDTO;
import cn.yuanyuan.community.community.dto.PageDTO;
import cn.yuanyuan.community.community.dto.QuestionDTO;
import cn.yuanyuan.community.community.po.GatoArticle;
import cn.yuanyuan.community.community.po.GatoComment;
import javafx.util.Pair;

/**
 * @author yuanyuan
 * #create 2020-02-27-10:28
 */
public interface ArticleService {
    /**添加文章*/
    void addArticle(GatoArticle gatoArticle);
    /**补全页面信息*/
    void getQuestionsPage(PageDTO<QuestionDTO> page);
    /**根据作者补全页面信息*/
    void getQuestionsPageByAuthor(PageDTO<QuestionDTO> page,Long author);
    /**得到文章详情*/
    QuestionDTO getQuestionDTOById(Long articleId);
    /**修改文章*/
    void modifyArticle(GatoArticle gatoArticle);
    /**增加页面浏览量*/
    void increViewCount(Pair<String,Long> pair);
    /**添加评论*/
    void addComment(GatoComment gatoComment);
    /**补全评论信息*/
    void getCommentPage(PageDTO<GatoCommentDTO> pageDTO, Long articleId);
}
