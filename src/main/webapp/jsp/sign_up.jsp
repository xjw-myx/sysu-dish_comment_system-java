<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中大食评-注册</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap/css/login_signup.css" />
	<style>
        body {
            background: url("${pageContext.request.contextPath}/static/images/Background/sign_up.jpg") no-repeat center 0px;
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
        <h2>账号注册</h2>
        <% 
            Integer return_code = (Integer) session.getAttribute("return_code");
            if (return_code != null) {
                if (return_code == 4) {
                    out.println("<div class='alert' style='color:red'>用户ID已存在，注册失败！</div>");
                    out.println("<div class='alert' style='color:red'>请勿冒用他人学号！</div>");
                }
            }
            session.removeAttribute("returnCode");
        %>
        <form method="post" action="sign_up" onsubmit="return validateForm()">
            <div class="login-field">
                <input type="text" id="user_id" name="user_id" required />
                <label>学号（作为您的用户ID）</label>
            </div>
            <div class="login-field">
                <input type="text" id="user_name" name="user_name" required />
                <label>用户名</label>
            </div>
            <div class="login-field">
                <input type="password" id="password" name="password" required />
                <label>密码</label>
            </div>
            <div class="login-field">
                <input type="password" id="confirm-password" name="confirm-password" required />
                <label>确认密码</label>
                <p id="passwordMatchError" class="text-danger" style="display:none; color:red">两次密码不匹配！请重新输入。</p>
            </div>
            <button type="submit">注册</button>
            <div>
                <a href="/test2/log_in" style="color:white">已有账号，前往登录</a>
                </br>
                <a href="/test2" style="color:white">返回首页</a>
            </div>
        </form>
    </div>

	<script>
		function validateForm() {
			var password = document.getElementById("password").value;
			var confirmPassword = document.getElementById("confirm-password").value;
			var passwordMatchError = document.getElementById("passwordMatchError");
			
			if (password !== confirmPassword) {
				passwordMatchError.style.display = "block";
				return false; // 不予注册
			} else {
				passwordMatchError.style.display = "none";
				return true; // 可以注册
			}
		}
	</script>
</body>
</html>
