<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="data_list ">
    <div id="newsListNav">
        ${newsNav}
    </div>
    <div class="datas" >
        <input type="hidden" id="newsId" value="${news.newsId}">
        <h3 class="news_title" >${news.title}</h3>
        <div class="row news_info">
            <div class="col-md-4">
                日期：【<fmt:formatDate value="${news.publishDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>】
            </div>
            <div class="col-md-4">
                作者：${news.author}
            </div>
            <div class="col-md-4">
                访问次数：[${news.click}]
            </div>
        </div>
        <div class="news_content">
            ${news.context}
        </div>
    </div>
    <div class="newsUpAndDown">
        ${newsUpAndDown}
    </div>

</div>
<div class="comment_area">
    <h4>发表评论</h4>
    <textarea wrap="virtual" maxlength="200"  id="comment_text"></textarea>
    <div>
        <input type="button" value="评论提交" class="btn btn-primary">
    </div>
    <div class="comment_list" >
        <ul class="list-group">
            <c:forEach items="${commentList}" var="comment">
                <li class="list-group-item"><b>${comment.ipAddr}:[<fmt:formatDate value="${comment.commentDate}" pattern="yyyy-MM-dd HH:mm:ss"  />]:</b>${comment.content}</li>
            </c:forEach>
        </ul>
    </div>

</div>
<script>
    $(function () {
        $("input[value=评论提交]").click(function () {
            var strContent=$("#comment_text").val()
            if(strContent.trim()==""){
                alert("请在评论区写入信息")
                return;
            }
            $.ajax({
                type:"post",
                url:"CommentServlet",
                data:"action=add&newsId="+$("#newsId").val()+"&content="+strContent,
                success:function (msg) {
                    if(msg!=""){
                        alert("评论成功")
                        // 清空评论框
                        $("#comment_text").val("");
                        // 后台传的是json字符串  将json字符串转为js对象
                        var comment= eval("("+msg+")");
                        // 获取到的时间
                        var commentDate = getcommentDate(comment.commentDate);
                        // 用jquery创建一个li标签
                        var oLi = $("<li class='list-group-item'><b>"+comment.ipAddr+":["+commentDate+"]:</b>"+comment.content+"</li>");
                        if($(".list-group").children().length==0){
                            $(".list-group").append(oLi);
                        }else {
                            $(oLi).insertBefore($(".list-group").children().eq(0));
                        }
                    }
                }
            })

        })
    })
    function getcommentDate(commentDate){
        var date = new Date(commentDate);
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var day = date.getDate();


        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();

        month = getStNumberr(month);
        day = getStNumberr(day);
        hours = getStNumberr(hours);
        minutes = getStNumberr(minutes);
        seconds = getStNumberr(seconds);
        return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    }

    function getStNumberr(num) {
        if(num<10){
            num="0"+num;
        }
        return num;

    }

</script>