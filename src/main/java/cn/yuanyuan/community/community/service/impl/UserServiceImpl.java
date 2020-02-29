package cn.yuanyuan.community.community.service.impl;

import cn.yuanyuan.community.community.dto.AccessTokenDTO;
import cn.yuanyuan.community.community.exception.GitHubException;
import cn.yuanyuan.community.community.mapper.GatoArticleMapper;
import cn.yuanyuan.community.community.mapper.GatoUserMapper;
import cn.yuanyuan.community.community.po.GatoUser;
import cn.yuanyuan.community.community.po.GitHubUser;
import cn.yuanyuan.community.community.provider.GDUFEAD;
import cn.yuanyuan.community.community.provider.GithubProvider;
import cn.yuanyuan.community.community.service.UserService;
import cn.yuanyuan.community.community.util.HttpClientUtils;
import cn.yuanyuan.community.community.util.JjwtUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

/**
 * @author yuanyuan
 * #create 2020-02-23-10:43
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${oauth.github.client_id}")
    private String clientId;
    @Value("${oauth.github.client_secret}")
    private String clientSecret;
    @Value("${oauth.github.redirect_uri}")
    private String redirectUri;
    @Autowired
    GatoUserMapper gatoUserMapper;
    @Autowired
    GatoArticleMapper gatoArticleMapper;
    @Autowired
    GithubProvider githubProvider;

    @Override
    public void updateUser(GatoUser user) {

        _setToken(user);
        Date now = new Date(System.currentTimeMillis());
        user.setUserGmtCreate(now);
        user.setUserGmtModified(now);
        final Boolean exist = gatoUserMapper.isExistById(user.getUserId());
        if (exist) {
            gatoUserMapper.updateLoginState(user);
        } else {
            gatoUserMapper.insert(user);
        }
    }

    /**
     * 设置用户token
     * @param user
     */
    private void _setToken(GatoUser user) {
        try {
            final String token = JjwtUtils.createJWT(user.getUserId(), 64 * 64 * 1024);
            user.setUserToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取得github用户信息
     *
     * @param code
     * @param state
     * @return github用户信息
     */
    @Override
    public GitHubUser getGitHubUser(String code, String state) throws GitHubException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        final String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        return githubProvider.getGitHubUser(accessToken);
    }

    /**
     * 登录教务系统
     * @param id
     * @param password
     * @return
     */
    @Override
    public GatoUser loginEAD(Long id, String password) {
        final GatoUser gatoUser = gatoUserMapper.selectById(id);
        if (gatoUser != null) {
            final String userGdufPwd = gatoUser.getUserGdufPwd();
            if(password!=null || password.equals(userGdufPwd)){
                _setToken(gatoUser);
                return gatoUser;
            }else{
                return null;
            }
        } else {
            try {
                final CloseableHttpClient client = HttpClientUtils.getClient();
                final boolean loginEAD = GDUFEAD.loginEAD(String.valueOf(id), password, client);
                if (!loginEAD) {
                    return null;
                } else {
                    final Map<String, String> map = GDUFEAD.getName(client);
                    GatoUser user = new GatoUser();
                    user.setUserId(Long.valueOf(map.get("id")));
                    user.setUserName(map.get("name"));
                    user.setUserGdufPwd(password);
                    updateUser(user);
                    return user;
                }
            } catch (Exception e) {
                throw new RuntimeException("登录失败",e);
            }
        }
    }
}
