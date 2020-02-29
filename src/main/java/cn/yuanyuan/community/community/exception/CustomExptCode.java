package cn.yuanyuan.community.community.exception;

/**
 * @author yuanyuan
 * #create 2020-02-27-10:11
 */
public enum CustomExptCode implements ICustomExptCode {
    PARAMEATER_ERROR(2001, "参数错误"),
    GITHUB_ERROR(2002,"github登录功能暂不可用" );

    private String message;
    private Integer code;

    CustomExptCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
