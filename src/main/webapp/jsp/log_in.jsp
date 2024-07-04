<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中大食评-登录</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap/css/login_signup.css" />
	<style>
        body {
            background: url('<%= request.getAttribute("backgroundImage") %>') no-repeat center 0px;
            background-size: cover;
            background-position: center 0;
            background-repeat: no-repeat;
            background-attachment: fixed;
            -webkit-background-size: cover;
            -o-background-size: cover;
            -moz-background-size: cover;
            -ms-background-size: cover;
        }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>账号登录</h2>
        <div>
            <% 
			    Integer return_code = (Integer) session.getAttribute("return_code");
			    if (return_code != null) {
			        if(return_code == 3)      out.println("<p style=\"color: white;\">注册成功!请登录……</p>");
			        else if(return_code == 2) out.println("<p style=\"color: white;\">登陆失败！请重试……</p>");
			    }
			%>
        </div>
        <form method="post" action="log_in">
            <div class="login-field">
                <input type="text" name="user_id" required />
                <label>用户 ID</label>
            </div>
            <div class="login-field">
                <input type="password" name="password" required />
                <label>密码</label>
            </div>
            <button type="submit">登录</button>
            <div>
	            <a href="/test2/sign_up" style="color:white">没有账号？前往注册</a>
	            </br>
	            <a href="/test2" style="color:white">返回首页</a>
            </div>
        </form>
    </div>

</body>
</html>
