<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>中大食评-主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
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
    <!-- Navigation Bar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <b class="navbar-brand" >中大食评-主页</b>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li id="accountButton" style="display:none;"><a href="user_info">查看账户信息</a></li>
                <li id="loginButton"><a href="log_in">登录 / 注册</a></li>
                <li id="infoButton"><a href="about_us">关于我们</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 column">
                <div id="myCarousel" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        ${indicators}
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        ${slides}
                    </div>

                    <!-- Left and right controls -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>

            <div class="col-md-6 column">
                <div class="jumbotron">
                    <div class="comments-section">
                        <h1>评论</h1>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>菜品</th>
                                    <th>评分</th>
                                    <th>评价</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="comment" items="${comments}">
                                    <tr>
                                        <td><c:out value="${comment[0]}"/></td>
                                        <td><c:out value="${comment[1]}"/></td>
                                        <td><c:out value="${comment[2]}"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <p><a class="btn btn-primary btn-large" href="search">查询更多评论</a></p>
                </div>
            </div>
        </div>
    </div>

    <footer>
        <div class="container">
            <h4>中山大学 2024 春季 软件工程期末大作业</h4>
            <div id="current-time"></div>
        </div>
    </footer>

    <script>
        var isLoggedIn = ${return_code}; 

        // 检查登录状态并显示不同的按钮 1是登录
        $(document).ready(function() {
            if (isLoggedIn == 1) {
                $('#loginButton').hide();
                $('#accountButton').show();
            } else {
                $('#loginButton').show();
                $('#accountButton').hide();
            }
        });
    </script>
    <script>
	    function updateCurrentTime() {
	        var now = new Date();
	        var year = now.getFullYear();
	        var month = now.getMonth() + 1;
	        var date = now.getDate();
	        var hours = now.getHours();
	        var minutes = now.getMinutes();
	        var seconds = now.getSeconds();
	        
	        var monthString = month < 10 ? "0" + month : month;
	        var dateString = date < 10 ? "0" + date : date;
	        var hoursString = hours < 10 ? "0" + hours : hours;
	        var minutesString = minutes < 10 ? "0" + minutes : minutes;
	        var secondsString = seconds < 10 ? "0" + seconds : seconds;
	        
	        var currentTimeString = year + "-" + monthString + "-" + dateString + " " + 
	                                hoursString + ":" + minutesString + ":" + secondsString;
	        document.getElementById("current-time").innerText = "当前时间：" + currentTimeString;
	    }

        // 每秒更新一次时间
        setInterval(updateCurrentTime, 1000);
    </script>

</body>
</html>
