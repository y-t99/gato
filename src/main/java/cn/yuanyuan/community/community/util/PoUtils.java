package cn.yuanyuan.community.community.util;

import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.po.GitHubUser;

/**
 * @author yuanyuan
 * #create 2020-02-21-23:35
 */
public class PoUtils {
    public static GatoUser gitHubUserToGatoUser(GitHubUser gitHubUser){
        final GatoUser gatoUser = new GatoUser();
        final String name = gitHubUser.getName();
        if(name==null || "".equals(name.trim())) {
            gatoUser.setUserName(gitHubUser.getLogin());
        }else{
            gatoUser.setUserName(name);
        }
        gatoUser.setUserBio(gitHubUser.getBio());
        gatoUser.setUserEmail(gitHubUser.getEmail());
        gatoUser.setUserId(gitHubUser.getId());
        gatoUser.setUserPortrait(gitHubUser.getAvatarUrl());
        return gatoUser;
    }
}
