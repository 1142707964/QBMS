<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/17
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>题库管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>题库管理系统</h1>
    <div class="publicHeaderR">
        <p>
            <%
            Date date = new Date();
            int hours = date.getHours();
            if (hours >= 7 && hours < 11){
                out.println("早上好！");
            }else if (hours >= 11 && hours < 13){
                out.println("中午好！");
            }else if (hours >= 13 && hours <= 18){
                out.println("下午好！");
            }else{
                out.println("晚上好！");
            }
            %>
            <span style="color: #fff21b">${userSession.realName}</span>
            <c:if test="${userSession == null}">
                <c:out value="游客"></c:out>
            </c:if>
            <c:if test="${userSession.role == '管理员'}">
                <c:out value="管理员"></c:out>
            </c:if>
            <c:if test="${userSession.role == '老师'}">
                <c:out value="老师"></c:out>
            </c:if>
            <c:if test="${userSession.role == '学委'}">
                <c:out value="同学"></c:out>
            </c:if>
            <c:if test="${userSession.role == '学生'}">
                <c:out value="同学"></c:out>
            </c:if>，欢迎您！
        </p>
        <a href="${pageContext.request.contextPath}/logout.do">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2022年12月30日20:28星期五</span>
</section>
<!--主体内容-->
<section class="publicMian">

