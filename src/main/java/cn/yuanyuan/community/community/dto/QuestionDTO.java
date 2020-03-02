package cn.yuanyuan.community.community.dto;

import cn.yuanyuan.community.community.po.GatoArticle;
import cn.yuanyuan.community.community.po.GatoTag;
import cn.yuanyuan.community.community.po.GatoUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-24-19:07
 */
@Data
public class QuestionDTO {
    private GatoArticle gatoArticle;
    private GatoUser gatoUser;
    private List<GatoArticle> abouts;
    private List<String> tags;
}
