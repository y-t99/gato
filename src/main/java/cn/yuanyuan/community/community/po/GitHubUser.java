package cn.yuanyuan.community.community.po;

import lombok.Data;

/**
 * @author yuanyuan
 * #create 2020-02-20-21:17
 */
@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String login;
    private String avatarUrl;
    private String email;
}
