package cn.yuanyuan.community.community.po;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (GatoArticle)实体类
 *
 * @author makejava
 * @since 2020-02-23 10:29:31
 */
@Data
public class GatoArticle implements Serializable {
    private static final long serialVersionUID = -40144435909044169L;
    //文章id
    private Long articleId;
    //文章标题
    private String articleTitle;
    //文章简介
    private String articleDescription;
    //记录创建时间
    private Date articleGmtCreate;
    //记录修改时间
    private Date articleGmtModified;
    //文章浏览量
    private Integer articleViewCount;
    //文章点赞量
    private Integer articleLikeCount;
    //文章回复数量
    private Integer articleCommentCount;
    //标签
    private String articleTag;
    //作者id
    private Long articleAuthor;
}