<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/2/4
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/normal/funclist.jsp"%>

<style type="text/css">
    .normalTable {

    }
    .normalTable tr {

    }
    .normalTable td{
        height: 160px;
        width: 160px;
        border-radius: 30px;
        margin-left: 90px;
        margin-bottom: 30px;
        text-align: center;
        line-height: 150px;
        float: left;
        font-size: 18px;
        color: #eeeeee;
        /*border: 1px solid #4987c0;*/
        background: #44b6ed;
        opacity: 0.8;
    }
    .normalTable td:hover{
        background: #318ef1;
    }
    .normalTable td:active{
         background: #096ad2;
     }
    /*.normalTable td a{*/
    /*    font-size: 20px;*/
    /*    !*color: #335ece;*!*/
    /*    color: #ffffff;*/
    /*    !*opacity: 0.9;*!*/
    /*}*/
</style>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是：</strong>
        <span>我的课程页面</span>
    </div>
    <div class="banner">
        <img src="/images/banner5.jpg" alt="课程管理banner">
    </div>
    <div class="search">
<%--        <a href="javascript:;" id="addCourse">添加课程</a>--%>
    </div>
    <!--通用表格样式-->
    <table class="normalTable" cellpadding="0" cellspacing="0">
        <tr>
            <c:forEach var="course" items="${courseList}" varStatus="status">
                <td class="courseTD" cname="${course.name}">
<%--                    <a href="/jsp/qbank.do?method=query&queryCourseName=${course.name}">${course.name}</a>--%>
                    ${course.name}
                </td>
            </c:forEach>
        </tr>
    </table>
</div>
</section>

<!--点击添加按钮后弹出的页面-->
<div id="remove1">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <form action="${pageContext.request.contextPath}/jsp/course.do" id="addForm">
                <p>请输入科目：<input type="text" name="newCourse" id="newCourse" placeholder="科目" required></p>
                <input type="hidden" name="method" value="add">
                <a href="#" id="yes_add" onclick="document.getElementById('addForm').submit()">确定</a>
                <a href="javascript:;" id="no_add">取消</a>
            </form>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/courselist.js"></script>
