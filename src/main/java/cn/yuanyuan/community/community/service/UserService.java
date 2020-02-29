package cn.yuanyuan.community.community.service;

import cn.yuanyuan.community.community.exception.GitHubException;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.po.GitHubUser;


/**
 * @author yuanyuan
 * #create 2020-02-23-10:41
 */
public interface UserService {
    /**更新用户信息*/
    void updateUser(GatoUser gatoUser);
    /**获取github用户信息*/
    GitHubUser getGitHubUser(String code,String state) throws GitHubException;
    /**获取问题列表*/
//    List<QuestionDTO> getQuestionsDTO(int offset,int rows);
    /**登录教务系统*/
    GatoUser loginEAD(Long id,String password);
}
