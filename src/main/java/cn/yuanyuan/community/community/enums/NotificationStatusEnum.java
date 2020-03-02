package cn.yuanyuan.community.community.enums;

import lombok.Data;

/**
 * @author yuanyuan
 * #create 2020-03-02-8:15
 */
public enum NotificationStatusEnum {
    NOTIFICATION_STATUS_UNREAD(0, "未读"),
    NOTIFICATION_STATUS_READ(1, "已读");
    private Integer status;
    private String message;

    NotificationStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }}
