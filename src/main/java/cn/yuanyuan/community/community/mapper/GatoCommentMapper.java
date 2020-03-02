package cn.yuanyuan.community.community.mapper;

import cn.yuanyuan.community.community.po.GatoArticle;
import cn.yuanyuan.community.community.po.GatoComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-27-10:53
 */
@Repository
public interface GatoCommentMapper {
    @Insert("insert into gato.gato_comment(comment_user_id, comment_content_id, comment_content, comment_gmt_create_time, comment_gmt_modified_time, comment_type)" +
            "        values (#{commentUserId}, #{commentContentId}, #{commentContent}, #{commentGmtCreateTime}, #{commentGmtModifiedTime}, #{commentType})")
    void insert(GatoComment gatoComment);
    @Select("select count(*) from gato.gato_comment where comment_content_id=#{commentContentId}")
    int queryCountByArticleId(long commentContentId);

    @Select("select * from gato.gato_comment where comment_content_id=#{commentContentId} order by comment_gmt_create_time desc limit #{begin},#{rows} ")
    List<GatoComment> query(int begin, int rows,long commentContentId);
}
