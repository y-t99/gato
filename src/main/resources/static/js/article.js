/**
 * 评论提交
 */
$("#commentSubmit").click(function () {
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
            console.log(data);
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
let articleId=$('#articleId').val();
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
    path:"/article/comment/" +articleId
};
let getComments=()=>{
    $.ajax({
        url: commentPage.path+'/'+commentPage.rows+'/'+commentPage.currentPage,
        type: "get",
        contentType:"application/json",
        dataType:"json",
        data:"",
        success: function(data){
            commentPage=data;
            setNavPage();
            setComments();
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
        commentLi.find("#commentGmtCreateTime").text(getDate(comment.commentGmtCreateTime));
        commentsUl.append($('<hr/>'));
        commentsUl.append(commentLi.clone());
    });
};
let getDate=(time)=>{
    let temp=new Date(time);
    return temp.getFullYear()+'/'+temp.getMonth()+'/'+temp.getDay()+'  '
    +temp.getHours()+':'+temp.getMinutes()+":"+temp.getSeconds();
};
/**
 * 分页条
 */
let navPageUl=$('#nav-page ul');
let navPre=$('#nav-page #Previous');
let navNext=$('#nav-page #Next');
let setNavPage=()=>{
    navPageUl.html("");
    if (commentPage.isPrevious){
        navPage.find('a').attr("href",commentPage.path+'/'+commentPage.rows+'/'+(commentPage.currentPage-1));
        navPageUl.append(navPre);
    }
    for (let i = 0; i < commentPage.pages.length; i++) {
        let page=commentPage.pages[i];
        let li=$('<li></li>');
        let link=$('<a>'+page+'</a>');
        link.attr("href",commentPage.path+'/'+commentPage.rows+'/'+page);
        li.append(link);
        if(page===commentPage.currentPage){
            li.addClass('active');
        }
        navPageUl.append(li);
    }
    if (commentPage.isAfter){
        navNext.find('a').attr("href",commentPage.path+'/'+commentPage.rows+'/'+(commentPage.currentPage+1));
        navPageUl.append(navNext);
    }
};

$(function () {
    getComments();
});