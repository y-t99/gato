package cn.yuanyuan.community.community.po;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (GatoUser)实体类
 *
 * @author makejava
 * @since 2020-02-21 04:21:57
 */
@Data
public class GatoUser implements Serializable {
    private static final long serialVersionUID = -13316382637060538L;
    //用户主键
    private Long userId;
    //用户头像地址
    private String userPortrait;
    //用户创建时间
    private Date userGmtCreate;
    //用户修改时间
    private Date userGmtModified;
    //用户姓名
    private String userName;
    //用户账号
    private String userAccountId;
    //用户信息
    private String userBio;
    //用户token
    private String userToken;
    //用户邮箱
    private String userEmail;
    //用户教务系统密码
    private String userGdufPwd;
}