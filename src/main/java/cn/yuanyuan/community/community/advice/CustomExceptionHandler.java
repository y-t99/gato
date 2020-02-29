package cn.yuanyuan.community.community.advice;

import cn.yuanyuan.community.community.dto.ResultDTO;
import cn.yuanyuan.community.community.exception.CustomExptCode;
import cn.yuanyuan.community.community.exception.GitHubException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-26-9:54
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GitHubException.class)
    public String githubHandler(HttpServletRequest request){
        request.setAttribute("javax.servlet.error.status_code",500);
        request.setAttribute("result",ResultDTO.errOf(CustomExptCode.GITHUB_ERROR));
        return "forward:/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String bindException(HttpServletRequest request){
        request.setAttribute("javax.servlet.error.status_code",400);
        request.setAttribute("result",ResultDTO.errOf(CustomExptCode.PARAMEATER_ERROR));
        return "forward:/error";
    }

}
