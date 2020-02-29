package cn.yuanyuan.community.community;

import cn.yuanyuan.community.community.dto.PageDTO;
import cn.yuanyuan.community.community.util.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yuanyuan
 * #create 2020-02-22-16:34
 */
@SpringBootTest
public class Test {
    @org.junit.jupiter.api.Test
    public void test() throws Exception {
//        final String jwt = JjwtUtils.createJWT(123456, 10 * 1024);
//        System.out.println(jwt);
        final Claims claims = JjwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEyMzQ1NiwiaWF0IjoxNTgyMzYwNzkwLCJleHAiOjE1ODIzNjA4MDB9.Os_JulmkA_39X9U2q72zQb6iAIQGvpxNiHG2QZAyvt8");
        System.out.println(claims.get("userId",Integer.class));
    }

    @org.junit.jupiter.api.Test
    public void pageDTOTest(){
        final PageDTO<Object> page = new PageDTO<>();
        page.setTotalPage(10);
        page.setCurrentPage(1);
        System.out.println(page);
        page.setCurrentPage(2);
        System.out.println(page);
        page.setCurrentPage(3);
        System.out.println(page);
        page.setCurrentPage(4);
        System.out.println(page);
        page.setCurrentPage(5);
        System.out.println(page);
        page.setCurrentPage(6);
        System.out.println(page);
        page.setCurrentPage(7);
        System.out.println(page);
        page.setCurrentPage(8);
        System.out.println(page);
        page.setCurrentPage(9);
        System.out.println(page);
        page.setCurrentPage(10);
        System.out.println(page);

    }
}
