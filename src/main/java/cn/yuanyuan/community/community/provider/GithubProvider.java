package cn.yuanyuan.community.community.provider;

import cn.yuanyuan.community.community.dto.AccessTokenDTO;
import cn.yuanyuan.community.community.exception.GitHubException;
import cn.yuanyuan.community.community.po.GitHubUser;
import cn.yuanyuan.community.community.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author yuanyuan
 * #create 2020-02-20-19:29
 */
@Component
public class GithubProvider {
    /**
     * 返回github给的token
     * @param accessTokenDTO
     * @return accessToken
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws GitHubException {
        HttpPost post=new HttpPost("https://github.com/login/oauth/access_token");
        try {
            String response=HttpClientUtils.doPost(post,accessTokenDTO);
            return response.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new GitHubException("githubtoken获取失败");
    }

    /**
     * 获取github用户信息
     * @param accessToken
     * @return github用户信息
     */
    public GitHubUser getGitHubUser(String accessToken){
        String url="https://api.github.com/user";
        HttpGet get=new HttpGet(url);
        get.addHeader(new BasicHeader("Authorization","token "+accessToken));
        try {
            String info=HttpClientUtils.doGet(get);
            return JSON.parseObject(info,GitHubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("github用户信息获取失败");
    }
}
