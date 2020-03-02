package cn.yuanyuan.community.community.mapper;

import cn.yuanyuan.community.community.po.GatoNotification;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @author yuanyuan
 * #create 2020-03-02-8:32
 */
@Repository
public interface GatoNotificationMapper {
    @Insert("insert into gato.gato_notification(notification_notifier,notification_receiver,notification_outer_id" +
            " ,notification_type,notification_gmt_creat,notification_status,notification_name) " +
            " values(#{notificationNotifier},#{notificationReceiver},#{notificationOuterId}" +
            " ,#{notificationType},#{notificationGmtCreat},#{notificationStatus},#{notificationName})")
    void insert(GatoNotification gatoNotification);
}
