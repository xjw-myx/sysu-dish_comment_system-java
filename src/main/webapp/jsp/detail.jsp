<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>${dish_info.dish_name} 详细信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
    <h1 style="margin-top: 20px; margin-left: 20px;">${dish_info.dish_name}</h1>
    
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-6 column">
                <table class="table">
                    <tbody>
                        <tr>
                            <th>价格</th>
                            <td>${dish_info.dish_price}</td>
                        </tr>
                        <tr>
                            <th>供餐餐厅</th>
                            <td>${dish_info.cafeteria_name}</td>
                        </tr>
                        <tr>
                            <th>供餐时间</th>
                            <td>${dish_info.served_time_period}</td>
                        </tr>
                        <tr>
                            <th>图片</th>
                            <td><img src="${pageContext.request.contextPath}/static/images/Dish/<%= request.getParameter("dish_id") %>.png" alt="${dish_info.dish_name}" style="max-width: 200px;"></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-md-6 column">
                <table class="table">
                    <thead>
                        <tr>
                            <th>用户ID</th>
                            <th>评分</th>
                            <th>评价</th>
                        </tr>
                    </thead>
    
                    <tbody>
                        <c:forEach var="comment" items="${dish_comments}">
                            <tr>
                              <td>${comment.username_id}</td>
                              <td>${comment.score}</td>
                              <td>${comment.content}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty dish_comments}">
                            <tr>
                                <td colspan="3">暂无评论</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="container">
        <form method="post" action="" accept-charset="UTF-8">
            <div class="form-group">
                <label for="score">评分：</label>
                <input type="number" class="form-control" id="score" name="score" min="1" max="5">
            </div>
            <div class="form-group">
                <label for="content">评论内容：</label>
                <textarea class="form-control" id="content" name="content" rows="3"></textarea>
            </div>

            <c:if test="${comment_code == 1}">
                <div class="alert-success">评论成功</div>
            </c:if>
            <c:if test="${comment_code == 2}">
                <div class="alert-danger">评论失败！请登录后再评论...</div>
                <a href="log_in">前往登录</a>
            </c:if>
            <c:if test="${comment_code == 3}">
                <div class="alert-danger">评论失败！请输入评分和评价内容...</div>
            </c:if>

            <button type="submit" class="btn btn-primary">提交评论</button>
        </form>
        <a href="search">返回</a>
    </div>
</body>
</html>
