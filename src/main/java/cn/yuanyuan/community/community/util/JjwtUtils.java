package cn.yuanyuan.community.community.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanyuan
 * #create 2020-02-21-15:32
 */
public class JjwtUtils {

    private static final String JWT_SECRET = "a+YUtm7TF4i97SYWPSJrcQ==";

    /**
     * 生成token
     * @param userId
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJWT(Long userId, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //签发jwt的时间
        long nowMillis=System.currentTimeMillis();
        SecretKey key = generalKey();

        Map<String,Object> map = new HashMap<>();
        JwtBuilder builder = Jwts.builder()
                .addClaims(map)
                //存入用户信息
                .claim("userId", userId)
                //签证制作时间
                .setIssuedAt(new Date())
                //签证是所用的加密算法和密钥
                .signWith(signatureAlgorithm, key)
                //jwt的过期时间
                .setExpiration(new Date(nowMillis + ttlMillis));
        return builder.compact();
    }

    /**
     * 生成key
     * @return
     */
    private static SecretKey generalKey() {
        //秘钥
        String stringKey =JjwtUtils.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    /**
     * 解析token
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static void main(String[] args) {

    }
}
