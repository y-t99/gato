package cn.yuanyuan.community.community.po;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author yuanyuan
 * #create 2020-03-01-21:40
 */
@Component
@Data
public class GatoTag {
    @Autowired
    private Set<String> tags;
}
