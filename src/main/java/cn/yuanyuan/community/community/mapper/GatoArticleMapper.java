package cn.yuanyuan.community.community.mapper;

import cn.yuanyuan.community.community.po.GatoArticle;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-23-10:31
 */
@Repository
public interface GatoArticleMapper {
    @Insert("insert into gato.gato_article(article_title, article_description, article_gmt_create, article_gmt_modified, article_tag,article_author)" +
            "values (#{articleTitle}, #{articleDescription}, #{articleGmtCreate}, #{articleGmtModified}, #{articleTag}, #{articleAuthor})")
    void insert(GatoArticle gatoArticle);

    @Select("select count(*) from gato.gato_article")
    int queryCounts();

    @Select("select * from gato.gato_article order by article_gmt_create desc limit #{begin},#{rows} ")
    List<GatoArticle> queryByPage(int begin, int rows);

    @Select("select * from gato.gato_article WHERE article_author=#{author} order by article_gmt_create desc limit #{begin},#{rows}")
    List<GatoArticle> queryByPageAndAuthor(int begin, int rows, Long author);

    @Select("select count(*) from gato.gato_article WHERE article_author=#{author}")
    int queryCountsByAuthor(Long author);

    @Select("select * from gato.gato_article where article_id=#{articleId}")
    GatoArticle queryById(Long articleId);

    @Update("update gato.gato_article set article_title=#{articleTitle}," +
            " article_description=#{articleDescription},article_tag=#{articleTag}" +
            " where article_id=#{articleId} ")
    void updateById(GatoArticle gatoArticle);

    @Update("update gato.gato_article set article_view_count=article_view_count+1 where article_id=#{articleId}")
    void increViewCount(Long articleId);
    @Update("update gato.gato_article set article_comment_count=article_comment_count+1 where article_id=#{articleId}")
    void increCommentCount(Long articleId);
    @Select("select * FROM  gato.gato_article WHERE article_tag REGEXP #{condition} limit 0,10")
    List<GatoArticle> queryByTags(String condition);
}
