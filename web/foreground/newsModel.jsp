<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>天天新闻网</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/static/css/news.css">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="<%=request.getContextPath() %>/static/js/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <%--  动态引入文件  --%>
    <jsp:include page="commons/header.jsp"></jsp:include>
    <div class="row">
        <div class="col-md-8">
            <jsp:include page="${mainJsp}"></jsp:include>
        </div>
        <div class="col-md-4">
                <div class="data_list "style="height: 300px">
                <div class="dataHeader">最新新闻</div>
                <div class="datas">
                    <ul>
                        <c:forEach items="${newNewsList}" var="newNews">
                            <li>
                            <a href="NewsServlet?action=queryOne&newsId=${newNews.newsId}" title="${newNews.title}" >
                                    ${fn:length(newNews.title)>15?fn:substring(newNews.title, 0, 15).concat("..."):newNews.title}
                            </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="data_list "style="height: 300px;margin-top: 15px">
                <div class="dataHeader">热门新闻</div>
                <div class="datas">
                    <ul>
                        <c:forEach items="${clickNewsList}" var="clickNews">
                            <li>
                            <a href="NewsServlet?action=queryOne&newsId=${clickNews.newsId}" title="${clickNews.title}" >
                                    ${fn:length(clickNews.title)>15?fn:substring(clickNews.title, 0, 15).concat("..."):clickNews.title}
                            </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>





        </div>
    </div>
    <jsp:include page="commons/footer.jsp"></jsp:include>
</div>
</body>