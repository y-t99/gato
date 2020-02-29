package cn.yuanyuan.community.community.dto;

import cn.yuanyuan.community.community.po.GatoArticle;
import cn.yuanyuan.community.community.po.GatoComment;
import cn.yuanyuan.community.community.po.GatoUser;
import lombok.Data;

/**
 * @author yuanyuan
 * #create 2020-02-27-10:07
 */
@Data
public class GatoCommentDTO {
    //用户
    private GatoComment gatoComment;
    //用户头像
    private String userPortrait;
    //用户昵称
    private String userNick;
}
