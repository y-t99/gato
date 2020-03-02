package cn.yuanyuan.community.community.mapper;

import cn.yuanyuan.community.community.po.GatoUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author yuanyuan
 * #create 2020-02-21-4:23
 */
@Repository
public interface GatoUserMapper {
    @Select("SELECT * FROM gato_user WHERE user_id=#{id}")
    GatoUser selectById(Long id);

    @Select("SELECT user_portrait FROM gato_user WHERE user_id=#{id}")
    String queryPortrait(Long id);

    @Select("SELECT count(*)=1 FROM gato_user WHERE user_id=#{id}")
    Boolean isExistById(Long id);

    @Select("SELECT * FROM gato_user WHERE user_token=#{token}")
    GatoUser selectByToken(String token);

    @Insert("INSERT INTO gato_user(user_id,user_portrait ,user_gmt_create ,user_gmt_modified," +
            "user_name,user_account_id,user_bio,user_token,user_email)" +
            "VALUES(#{userId},#{userPortrait},#{userGmtCreate},#{userGmtModified}," +
            "#{userName},#{userAccountId},#{userBio},#{userToken},#{userEmail})")
    void insert(GatoUser user);

    @Update("UPDATE gato_user SET user_token=#{userToken},user_gmt_modified=#{userGmtModified} WHERE user_id=#{userId}")
    void updateLoginState(GatoUser user);
}
