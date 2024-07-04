<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中大食评-账户信息</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" />
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <style>
        .navbar {
            background-color: #8cc6dd;
        }
        footer {
            background-color: #8cc6dd;
            padding: 20px 0;
            position: absolute;
            bottom: 0;
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <p class="navbar-brand" >中大食评-账户信息</p>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li id="infoButton"><a href="index">返回主页</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <table class="table">
                <tbody>
                    <tr>
                        <td><img src="${pageContext.request.contextPath}/static/images/about_us/user.png" alt="默认用户头像" class="img-fluid rounded-circle mb-3" style="max-width: 130px;"></td>
                    </tr>
                    <tr>
                        <th>用户名</th>
                        <td>${user_info.username}</td>
                    </tr>
                    <tr>
                        <th>用户ID</th>
                        <td>${user_info.user_id}</td>
                    </tr>
                </tbody>
            </table>

            <table class="table">
            	<h3>历史评论内容</h3>
                <tbody>
                    <c:forEach var="comment" items="${user_info.comments}">
                        <tr>
                            <td>${comment.username_id}</td>
                            <td>${comment.score}</td>
                            <td>${comment.content}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty user_info.comments}">
                        <tr>
                            <td colspan="3">暂未发表评论……</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <form method="POST" action="">
                <button type="submit" class="btn btn-danger">退出登录</button>
            </form>
        </div>
    </div>
</body>
</html>
