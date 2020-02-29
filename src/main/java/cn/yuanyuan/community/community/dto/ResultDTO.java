package cn.yuanyuan.community.community.dto;

import cn.yuanyuan.community.community.exception.CustomExptCode;
import lombok.Data;

/**
 * @author yuanyuan
 * #create 2020-02-27-10:17
 */
@Data
public class ResultDTO {
    private String message;
    private Integer code;
    private ResultDTO(Integer code, String message){
        this.message=message;
        this.code=code;
    }
    private ResultDTO(CustomExptCode customExptCode){
        this.message=customExptCode.getMessage();
        this.code=customExptCode.getCode();
    }

    public static ResultDTO errOf(String message,Integer code){
        return new ResultDTO(code, message);
    }

    public static ResultDTO errOf(CustomExptCode customExptCode){
        return new ResultDTO(customExptCode);
    }
    public static ResultDTO okOf(){
        return new ResultDTO(200, "响应成功");
    }
}
