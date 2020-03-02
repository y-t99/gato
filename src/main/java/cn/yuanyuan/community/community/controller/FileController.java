package cn.yuanyuan.community.community.controller;

import cn.yuanyuan.community.community.dto.FileDTO;
import cn.yuanyuan.community.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yuanyuan
 * #create 2020-03-02-11:38
 */
@Controller
public class FileController {
    @Autowired
    UCloudProvider uCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest= (MultipartHttpServletRequest) request;
        final MultipartFile file = multipartRequest.getFile("editormd-image-file");
        final FileDTO fileDTO = new FileDTO();
        try {
            final String url = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功");
            fileDTO.setUrl(url);
        } catch (IOException e) {
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
        }
        return fileDTO;
    }
}
