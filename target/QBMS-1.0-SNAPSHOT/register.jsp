<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/16
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>题库管理系统</title>
</head>
<link rel="stylesheet" href="css/common.css">
<style type="text/css">
    #register_box {
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
    #input_box{
        color: #fff;
    }
    #radio_box{
        color: #fff;
        margin-top: 20px;
    }
    #radio-box2{
        margin-left: 25px;
    }
    #back{
        position: fixed;
        right: 580px;
        top: 165px;
    }
    #success{
        color: #dbb137;
    }
</style>
<body>
<form action="/register.do" method="post">
    <div id="register_box">
        <h2>REGISTER</h2>
        <div id="success">${success}</div>
        <div id="input_box">
            用户名：<input type="text" placeholder="字母或数字" name="username" class="inputArea" required> <br>
            密&emsp;码：<input type="text" placeholder="6~12位" name="password" class="inputArea" required> <br>
            姓&emsp;名：<input type="text" placeholder="真实姓名" name="realname" class="inputArea" required> <br>
        </div>
        <div id="radio_box">
            <input type="radio" value="老师" name="role" id="radio-box1" disabled>老师
            <input type="radio" value="学生" name="role" id="radio-box2" checked>学生
        </div>
        <input type="submit" value="注册" id="button"> <br>
    </div>
</form>
<div id="back">
    <a href="login.jsp">返回</a>
</div>
</body>
</html>
