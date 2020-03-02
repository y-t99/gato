package cn.yuanyuan.community.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.*;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author yuanyuan
 * #create 2020-03-02-15:54
 */
@Service
public class UCloudProvider {
    @Value("${ucloud.token.public}")
    private String PUBLIC_KEY;
    @Value("${ucloud.token.private}")
    private String PRIVATE_KEY;
    @Value("${ucloud.bucketName}")
    private String BUCKETNAME;
    @Value("${ucloud.region}")
    private String REGION;
    @Value("${ucloud.expires}")
    private Integer EXPIRES;
    @Value("${ucloud.domain}")
    private String DOMAIN;
    public String upload(InputStream inputStream, String mimeType, String fileName) {
        // Bucket相关API的授权器
        ObjectAuthorization BUCKET_AUTHORIZER = new UfileObjectLocalAuthorization(
                PUBLIC_KEY, PRIVATE_KEY);

        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(REGION, DOMAIN);
        final String[] split = fileName.split("\\.");
        if (split.length > 1) {
            fileName= UUID.randomUUID().toString()+"."+split[split.length-1];
        } else {
            return null;
        }
        try {
            PutObjectResultBean response = UfileClient.object(BUCKET_AUTHORIZER, config)
                    .putObject(inputStream, mimeType)
                    .nameAs(fileName)
                    .toBucket(BUCKETNAME)
                    .execute();
            if (response!=null && response.getRetCode()==0){
                String url = UfileClient.object(BUCKET_AUTHORIZER, config)
                        .getDownloadUrlFromPrivateBucket(fileName, BUCKETNAME, EXPIRES)
                        .createUrl();
                return url;
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
        } catch (UfileServerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
