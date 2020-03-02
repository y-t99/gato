/**
 * 评论提交
 */
$("#commentSubmit").click(()=> {
    let commentContent=$("#commentContent2");
    let content=commentContent.val();
    if (content==null || content.trim()===""){
        alert("评论不能为空");
        return;
    }
    let str = $('#gatoComment').serialize();
    str= decodeURIComponent(str);
    let comment=getObj(str);
    $.ajax({
        url: "/article/comment",
        type: "post",
        contentType:"application/json",
        dataType:"json",
        data:JSON.stringify(comment),
        success: function(data){
            if (data.code===200){
                commentContent.val("");
                getComments();
            } else{
                alert(data.message);
            }
        }
    });
});
function getObj(str) {
    let arr = str.split('&');
    let obj = {};
    arr.map(function(item) {
        let tempArr = item.split('=');
        obj[tempArr[0]] = tempArr[1];
    });
    return obj;
}
/**
 * 评论页面
 */
let path="/article/comment/" +$('#articleId').val();
let commentPage={
    pages:[],
    isPrevious:false,
    isAfter:false,
    totalRows:"",
    totalPage:"",
    rows:10,
    currentRows:"",
    currentPage:1,
    objs:{},
};
let getComments=()=>{
    $.ajax({
        url: path+'/'+commentPage.rows+'/'+commentPage.currentPage,
        type: "get",
        contentType:"application/json",
        dataType:"json",
        data:"",
        success: function(data){
            commentPage=data;
            setNavPage();
            setComments();
            $("#replyNum").text(commentPage.totalRows);
        }
    });
};
/**
 * 评论数据
 */
let commentsUl=$('#comments');
let commentLi=$('#comment');
let setComments=()=> {
    commentsUl.html("");
    let comments=commentPage.objs;
    comments.forEach((commentdto)=>{
        let comment=commentdto.gatoComment;
        commentLi.find("#commentPortrait").attr("src",commentdto.userPortrait);
        commentLi.find("#commentUserNick").text(commentdto.userNick);
        commentLi.find("#commentContent").html(comment.commentContent);
        commentLi.find("#commentGmtCreateTime").text(new Date(comment.commentGmtCreateTime*1000).
                                toLocaleString('chinese',{hour12:false}));
        commentsUl.append($('<hr/>'));
        commentsUl.append(commentLi.clone());
    });
};
/**
 * 分页点击
 */
let navPageUl=$('#nav-page ul');
let navPre=$('#nav-page #Previous');
let navNext=$('#nav-page #Next');
let setNavPage=()=>{
    navPageUl.html("");
    if (commentPage.isPrevious){
        navPre.find('a').addClass("page-link-one");
        navPre.find('a').attr("value",commentPage.currentPage-1);
        navPageUl.append(navPre);
    }
    for (let i = 0; i < commentPage.pages.length; i++) {
        let page=commentPage.pages[i];
        let li=$('<li></li>');
        let link=$('<a>'+page+'</a>');
        link.attr("href","javascript:void(0);");
        link.addClass("page-link");
        li.append(link);
        if(page===commentPage.currentPage){
            li.addClass('active');
        }
        navPageUl.append(li);
    }
    if (commentPage.isAfter){
        navNext.find('a').addClass("page-link-one");
        navNext.find('a').attr("value",commentPage.currentPage+1);
        navPageUl.append(navNext);
    }
    $('.page-link').click((e)=>{pageLink(e)});
    $('.page-link-one').click((e)=>{pageLinkOne(e)});

};
let pageLink=(e)=>{
    let obj=e.currentTarget;
    commentPage.currentPage=obj.text;
    getComments();
};
let pageLinkOne=(e)=>{
    let obj=e.currentTarget;
    commentPage.currentPage=$(obj).attr("value");
    getComments();
};
/**
 * 回复
 */
function reply(e){
    let id='#reply-panel-'+e.getAttribute("data-id");
    $(id).toggleClass("in");
};
$(function () {
    getComments();
});