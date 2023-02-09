<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/2/2
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="left">
    <h2 class="leftH2"><span></span> 功能列表 <span></span></h2>
    <nav>
        <ul class="list">
            <li><a href="${pageContext.request.contextPath}/jsp/course.do?method=query">课程管理</a></li>
            <li><a href="${pageContext.request.contextPath}/jsp/qbank.do?method=query">题库管理</a></li>
            <li><a href="${pageContext.request.contextPath}/jsp/user.do?method=query">用户管理</a></li>
            <li><a href="${pageContext.request.contextPath}/jsp/teacher/pwdmodify.jsp">密码修改</a></li>
            <li><a href="${pageContext.request.contextPath}/logout.do">退出系统</a></li>
        </ul>
    </nav>
</div>