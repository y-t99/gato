package cn.yuanyuan.community.community.exception;

/**
 * @author yuanyuan
 * #create 2020-02-25-1:10
 */
public class GitHubException extends Exception{
    public GitHubException() {
    }

    public GitHubException(String message) {
        super(message);
    }

    public GitHubException(String message, Throwable cause) {
        super(message, cause);
    }

    public GitHubException(Throwable cause) {
        super(cause);
    }

    public GitHubException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
