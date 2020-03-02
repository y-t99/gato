package cn.yuanyuan.community.community.po;

import lombok.Data;

import java.io.Serializable;

/**
 * (GatoNotification)实体类
 *
 * @author makejava
 * @since 2020-03-02 08:02:32
 */
@Data
public class GatoNotification implements Serializable {
    private static final long serialVersionUID = -38336173918104807L;
    //通知id
    private Long notificationId;
    //通知人id
    private Long notificationNotifier;
    //接收人id
    private Long notificationReceiver;
    //文章/评论等id
    private Long notificationOuterId;
    //通知类型
    private Integer notificationType;
    //通知创建时间
    private Long notificationGmtCreat;
    //通知状态
    private Integer notificationStatus;
    //通知文章/评论简要
    private String notificationName;
}