package cn.yuanyuan.community.community.po;

import cn.yuanyuan.community.community.util.DateToLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * (GatoComment)实体类
 *
 * @author makejava
 * @since 2020-02-27 10:06:38
 */
@Data@Component
@ConfigurationProperties(prefix = "comment")
public class GatoComment implements Serializable {
    private static final long serialVersionUID = 139176836997366300L;
    //评论主键
    private Long commentId;
    //评论人
    @NotNull(message = "{comment.commentuseridmsg}")
    private Long commentUserId;
    //被评论内容
    @NotNull(message = "{comment.commentcontentidmsg}")
    private Long commentContentId;
    //评论内容
    @NotBlank(message = "{comment.commentcontentmsg}")
    private String commentContent;
    //插入时间
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date commentGmtCreateTime;
    //修改时间
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date commentGmtModifiedTime;
    //1：一级评论，2：二级评论
    @NotNull(message = "{comment.commenttypemsg1}")
    @Min(value = 1,message = "{comment.commenttypemsg2}")
    @Max(value = 2,message = "{comment.commenttypemsg2}")
    private Integer commentType;
}