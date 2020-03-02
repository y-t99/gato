package cn.yuanyuan.community.community.service.impl;

import cn.yuanyuan.community.community.dto.GatoCommentDTO;
import cn.yuanyuan.community.community.dto.PageDTO;
import cn.yuanyuan.community.community.dto.QuestionDTO;
import cn.yuanyuan.community.community.enums.NotificationStatusEnum;
import cn.yuanyuan.community.community.enums.NotificationTypeEnum;
import cn.yuanyuan.community.community.mapper.GatoArticleMapper;
import cn.yuanyuan.community.community.mapper.GatoCommentMapper;
import cn.yuanyuan.community.community.mapper.GatoNotificationMapper;
import cn.yuanyuan.community.community.mapper.GatoUserMapper;
import cn.yuanyuan.community.community.po.GatoArticle;
import cn.yuanyuan.community.community.po.GatoComment;
import cn.yuanyuan.community.community.po.GatoNotification;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.service.ArticleService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.*;

/**
 * @author yuanyuan
 * #create 2020-02-27-10:29
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    GatoUserMapper gatoUserMapper;
    @Autowired
    GatoArticleMapper gatoArticleMapper;
    @Autowired
    GatoCommentMapper gatoCommentMapper;
    @Autowired
    GatoNotificationMapper gatoNotificationMapper;

    @Override
    public void addArticle(GatoArticle gatoArticle) {
        final String tags = gatoArticle.getArticleTag();
        final Set<String> set = new HashSet<>(Arrays.asList(tags.split(" ")));
        StringBuilder sb = new StringBuilder();
        for (String tag :
                set) {
            sb.append(tag + " ");
        }
        gatoArticle.setArticleTag(sb.toString());
        gatoArticleMapper.insert(gatoArticle);
    }

    /**
     * 补全页面信息
     *
     * @param page
     */
    @Override
    public void getQuestionsPage(PageDTO<QuestionDTO> page) {
        getQuestionsPageByAuthor(page, null);
    }

    @Override
    public void getQuestionsPageByAuthor(PageDTO<QuestionDTO> page, Long author) {
        //1、获取总页数
        final int totalRows;
        if (author == null) {
            totalRows = gatoArticleMapper.queryCounts();
        } else {
            totalRows = gatoArticleMapper.queryCountsByAuthor(author);
        }
        final int totalPage = totalRows % page.getRows() == 0 ? totalRows / page.getRows() : totalRows / page.getRows() + 1;
        page.setTotalRows(totalRows);
        page.setTotalPage(totalPage);
        page.finishPage();
        //2、分页查询
        int offset = (page.getCurrentPage() - 1) * page.getRows();
        //3、设置一些其他参数
        final List<QuestionDTO> questionDTOs = _getQuestionsDTO(offset, page.getRows(), author);
        page.setCurrentRows(questionDTOs.size());
        page.setObjs(questionDTOs);
    }

    /**
     * 获取文章
     *
     * @param articleId
     * @return
     */
    @Override
    public QuestionDTO getQuestionDTOById(Long articleId) {
        GatoArticle gatoArticle = gatoArticleMapper.queryById(articleId);
        final GatoUser gatoUser = gatoUserMapper.selectById(gatoArticle.getArticleAuthor());
        final QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setGatoArticle(gatoArticle);
        questionDTO.setGatoUser(gatoUser);
//        标签
        final String[] strings = gatoArticle.getArticleTag().split(" ");
        List<String> tags = new ArrayList<>();
        for (String tag : strings) {
            tag = tag.trim();
            if (!tag.equals("")) {
                tags.add(tag);
            }
        }
        questionDTO.setTags(tags);
//        相关
        StringBuilder condition = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            if (i != 0) {
                condition.append("|");
            }
            condition.append(tags.get(i));
        }
        List<GatoArticle> abouts = gatoArticleMapper.queryByTags(condition.toString());
        questionDTO.setAbouts(abouts);
        return questionDTO;
    }

    private Set<Pair<String, Long>> pairs = new HashSet<>();

    /**
     * 增加浏览数
     *
     * @param pair
     */
    @Override
    public void increViewCount(Pair<String, Long> pair) {
        if (pairs.contains(pair)) {
            return;
        }
        pairs.add(pair);
        gatoArticleMapper.increViewCount(pair.getValue());
    }

    @Override
    @Transient
    public void addComment(GatoComment gatoComment) {
//      1、添加评论
        gatoCommentMapper.insert(gatoComment);
        gatoArticleMapper.increCommentCount(gatoComment.getCommentContentId());
//      2、更新通知
        final GatoArticle gatoArticle = gatoArticleMapper.queryById(gatoComment.getCommentContentId());
        GatoNotification notification = new GatoNotification();
        notification.setNotificationGmtCreat(System.currentTimeMillis());
        notification.setNotificationNotifier(gatoComment.getCommentUserId());
        notification.setNotificationReceiver(gatoArticle.getArticleAuthor());
        notification.setNotificationOuterId(gatoComment.getCommentContentId());
        notification.setNotificationType(NotificationTypeEnum.NOTIFICATION_TYPE_ARTICLE.getType());
        notification.setNotificationStatus(NotificationStatusEnum.NOTIFICATION_STATUS_UNREAD.getStatus());
        notification.setNotificationName(gatoArticle.getArticleTitle());
        gatoNotificationMapper.insert(notification);
    }

    @Override
    public void getCommentPage(PageDTO<GatoCommentDTO> pageDTO, Long articleId) {
        //1、补全页面信息
        final int count = gatoCommentMapper.queryCountByArticleId(articleId);
        pageDTO.setTotalRows(count);
        int totalPage = count % pageDTO.getRows() == 0 ? count / pageDTO.getRows() :
                count / pageDTO.getRows() + 1;
        pageDTO.setTotalPage(totalPage);
        final List<GatoComment> gatoComments = gatoCommentMapper.query((pageDTO.getCurrentPage() - 1) * pageDTO.getRows(),
                pageDTO.getRows(), articleId);
        List<GatoCommentDTO> list = new ArrayList<>(gatoComments.size());
        //2、获取用户头像
        gatoComments.stream().forEach((gatoComment) -> {
            final Long userId = gatoComment.getCommentUserId();
            final GatoUser user = gatoUserMapper.selectById(userId);
            GatoCommentDTO gatoCommentDTO = new GatoCommentDTO();
            gatoCommentDTO.setGatoComment(gatoComment);
            gatoCommentDTO.setUserPortrait(user.getUserPortrait());
            gatoCommentDTO.setUserNick(user.getUserName());
            list.add(gatoCommentDTO);
        });
        pageDTO.setCurrentRows(list.size());
        pageDTO.setObjs(list);
        pageDTO.finishPage();
    }


    @Override
    public void modifyArticle(GatoArticle gatoArticle) {
        gatoArticleMapper.updateById(gatoArticle);
    }

    /**
     * 活得页面内容
     *
     * @param offset
     * @param rows
     * @param author
     * @return 页面内容
     */
    private List<QuestionDTO> _getQuestionsDTO(int offset, int rows, Long author) {
        final List<GatoArticle> gatoArticles;
        if (author == null) {
            gatoArticles = gatoArticleMapper.queryByPage(offset, rows);
        } else {
            gatoArticles = gatoArticleMapper.queryByPageAndAuthor(offset, rows, author);
        }
        List<QuestionDTO> list = new ArrayList<>(gatoArticles.size());
        gatoArticles.stream().forEach(article -> {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setGatoArticle(article);
            questionDTO.setGatoUser(gatoUserMapper.selectById(article.getArticleAuthor()));
            list.add(questionDTO);
        });
        return list;
    }

}
