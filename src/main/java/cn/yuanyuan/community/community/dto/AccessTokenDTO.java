package cn.yuanyuan.community.community.dto;

import lombok.Data;

/**
 * @author yuanyuan
 * #create 2020-02-20-19:30
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
