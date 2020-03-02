package cn.yuanyuan.community.community.config;

import cn.yuanyuan.community.community.interceptor.UserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yuanyuan
 * #create 2020-02-22-16:48
 */
@Configuration
public class MyAppConfig implements WebMvcConfigurer {
    @Autowired
    private UserInfoInterceptor userInfoInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        //静态资源；  *.css , *.js
        //SpringBoot已经做好了静态资源映射
        registry.addInterceptor(userInfoInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/oauth/**","/druid/**");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    @Bean(name = "tags")
    public Set<String> tags(){
        Set<String> set=new HashSet<>();
        set.add("Spring");
        set.add("SpringBoot");
        set.add("SpringMVC");
        set.add("Java");
        set.add("JavaWeb");
        set.add("Mybatis");
        set.add("JVM");
        return set;
    }
}
