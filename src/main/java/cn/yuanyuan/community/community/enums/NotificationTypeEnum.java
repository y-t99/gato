package cn.yuanyuan.community.community.enums;

/**
 * @author yuanyuan
 * #create 2020-03-02-8:08
 * 通知类型
 */
public enum NotificationTypeEnum {
    NOTIFICATION_TYPE_ARTICLE(1, "评论了文章"),
    NOTIFICATION_TYPE_COMMENT(2, "回复了评论");

    private Integer type;
    private String message;

    /**
     * 通知类型
     */
    NotificationTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }}
