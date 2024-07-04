<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>中大食评 关于我们</title>
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
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <p class="navbar-brand">中大食评-关于我们</p>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li id="infoButton"><a href="index">返回首页</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
		<div class="text-center">
		    <img src="${pageContext.request.contextPath}/static/images/about_us/xjw2.jpg" alt="Person 2" class="img-fluid rounded-circle mb-3" style="max-width: 300px;">
		    <h3>姓名：许嘉瑋（21307275）</h3>
		    <h3>来自：中山大学 计算机学院 计算机科学与技术专业</h3>
		    <h4>简介：独立规划、完成了全项目，包括平台搭建、数据库设计与实现、前后端代码实现、文档撰写等工作。</h4>
		</div>
    </div>

    <footer>
        <div class="container">
            <h4>中山大学 2024 春季 软件工程期末大作业</h4>
            <div id="current-time"></div>
        </div>
    </footer>
    
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
