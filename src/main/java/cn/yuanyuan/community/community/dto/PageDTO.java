package cn.yuanyuan.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-23-12:26
 */
@Data
public class PageDTO<T> {
    /**页码*/
    private List<Integer> pages;
    /**是否有前一页*/
    private Boolean isPrevious;
    /**是否有后一页*/
    private Boolean isAfter;
    /**总记录数*/
    private Integer totalRows;
    /**总页数*/
    private Integer totalPage;
    /**每页固定记录数*/
    private Integer rows;
    /**当前页*/
    private Integer currentRows;
    /**当前页记录数*/
    private Integer currentPage;
    /**页面对象*/
    private List<T> objs;
    /**分页请求地址*/
    private String path;

    /**最大的展示页数*/
    private static final int MAX_PAGES=7;
    /**
     * currentPage，totalPage，推断出
     *  isPrevious，isAfter，pages
     */
    public void finishPage() {

        if (currentPage<=0){
            throw new RuntimeException("当前页不能小于1");
        }

        pages=new ArrayList<>();
        if(totalPage<=7){
            for (int i = 1; i <= totalPage; i++) {
                pages.add(i);
            }
        }else if (1<=currentPage&& currentPage<=4){
            for (int i = 1; i<=MAX_PAGES; i++) {
                pages.add(i);
            }
        }else if (currentPage<=totalPage &&currentPage>=totalPage-3){
            for (int i = totalPage;i>totalPage-MAX_PAGES ; i--) {
                pages.add(i);
            }
            Collections.sort(pages);
        } else {
            for (int i=currentPage-3;i<=currentPage+3;i++){
                pages.add(i);
            }
        }

//        if (pages.contains(1)){
//            isPrevious=false;
//        }else{
//            isPrevious=true;
//        }

//        if (pages.contains(totalPage)){
//            isAfter=false;
//        }else{
//            isAfter=true;
//        }

        if(currentPage==1){
            isPrevious=false;
        }else {
            isPrevious=true;
        }
        if (currentPage.equals(totalPage) || totalPage==0){
            isAfter=false;
        }else{
            isAfter=true;
        }
    }

}
