<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/18
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/admin/funclist.jsp"%>

<style type="text/css">
    .commonTable td a {
        /*height: 30px;*/
        margin-top: 0px;
        margin-right: 0px;
        /*display: block;*/
        /*display: inline-block;*/
        color: #335ece;
    }
</style>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是：</strong>
        <span>课程管理页面</span>
    </div>
    <div class="banner">
        <img src="/images/banner5.jpg" alt="课程管理banner">
    </div>
    <div class="search">
        <a href="javascript:;" id="addCourse">添加课程</a>
    </div>
    <!--通用表格样式-->
    <table class="commonTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="20%">课程编号</th>
            <th width="40%">课程名</th>
        </tr>
        <c:forEach var="course" items="${courseList}" varStatus="status">
            <tr>
                <td>
                    <span>${course.id}</span>
                </td>
                <td>
                    <span><a href="/jsp/qbank.do?method=query&queryCourseName=${course.name}">${course.name}</a></span>
                </td>
                <td>
<%--                    <span><a class="deleteCourse" href="javascript:;" cid=${course.id} cname=${course.name}><img src="${pageContext.request.contextPath}/images/schu.png" alt="删除" title="删除"/></a></span>--%>
                    <span><a href="/jsp/course.do?method=delete&cid=${course.id}&cname=${course.name}"><img src="${pageContext.request.contextPath}/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</section>

<!--点击添加按钮后弹出的页面-->
<div class="zhezhao"></div>
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
