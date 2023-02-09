<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/17
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>题库管理系统</title>
</head>
<link rel="stylesheet" href="css/common.css">
<style type="text/css">
    #login_box {
        width: 20%;
        height: 400px;
        /*background-color: rgba(0, 0, 0, 0.38);*/
        background-color: rgba(0, 0, 0, 0.7);
        margin: auto;
        margin-top: 10%;
        text-align: center;
        border-radius: 10px;
        padding: 50px 50px;
    }
    #error{
        color: red;
    }
</style>
<body>
<form action="/login.do" method="post">
    <div id="login_box">
        <h2>LOGIN</h2>
        <div id="error">${error}</div>
        <div>
            <input type="text" placeholder="请输入用户名" name="username" class="inputArea" required> <br>
            <input type="password" placeholder="请输入密码" name="password" class="inputArea" required> <br>
        </div>
        <input type="submit" value="登录" id="button"> <br>
        <div class="msg">
            没有账号？
            <a href="register.jsp">注册</a>
        </div>
    </div>
</form>
</body>
</html>