<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/21
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/admin/funclist.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是：</strong>
        <span>用户管理页面</span>
    </div>
    <div class="search">
        <form action="${pageContext.request.contextPath}/jsp/user.do">
            <input name="method" value="query" class="input-text" type="hidden">
            <span>用户名：</span>
            <input name="queryUserName" type="text" value="${queryUserName}">

            <span>姓名：</span>
            <input name="queryRealName" type="text" value="${queryRealName}">

            <span>角色：</span>
            <select name="queryUserRole">
                <option value="管理员端">全部</option>
                <option value="老师" ${queryUserRole == "老师" ? "selected=\"selected\"":"" }>老师</option>
                <option value="学委" ${queryUserRole == "学委" ? "selected=\"selected\"":"" }>学委</option>
                <option value="学生" ${queryUserRole == "学生" ? "selected=\"selected\"":"" }>学生</option>
            </select>

            <input type="hidden" name="pageIndex" value="1"/>
            <input	value="查 询" type="submit" id="searchbutton">
<%--            <a href="${pageContext.request.contextPath}/user.do">添加用户</a>--%>
        </form>
    </div>
    <!--通用表格样式-->
    <table class="commonTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="20%">用户编号</th>
            <th width="20%">用户名</th>
            <th width="20%">姓名</th>
            <th width="20%">角色</th>
        </tr>
        <c:forEach var="user" items="${userList}" varStatus="status">
            <tr>
                <td>
                    <span>${user.id}</span>
                </td>
                <td>
                    <span>${user.userName}</span>
                </td>
                <td>
                    <span>${user.realName}</span>
                </td>
                <td>
                    <span>${user.role}</span>
                </td>
                <td>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id}><img src="${pageContext.request.contextPath}/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id} realname=${user.realName}><img src="${pageContext.request.contextPath}/images/schu.png" alt="删除" title="删除"/></a></span>
<%--                    <span><a href="/jsp/user.do?method=delete&uid=${user.id}&uname=${user.realName}"><img src="${pageContext.request.contextPath}/images/schu.png" alt="删除" title="删除"/></a></span>--%>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%--底部页码显示--%>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <c:import url="/jsp/common/rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>
</div>
</section>

<!--点击修改按钮后弹出的页面-->
<div class="zhezhao"></div>
<div id="remove">
    <div class="remove" id="updateView">
        <div class="removerChid">
            <h2>提示</h2>
            <div class="removeMain">
              <span>角色：</span>
                <select name="newRole" id="role">
                    <option value="老师">老师</option>
                    <option value="学委">学委</option>
                    <option value="学生">学生</option>
                </select>
                <br>
                <a href="#" id="yes_update">确定</a>
                <a href="#" id="no_update">取消</a>
            </div>
        </div>
    </div>
</div>

<!--点击删除按钮后弹出的页面-->
<%--<div class="zhezhao"></div>--%>
<div id="remove1">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>您确定要删除该用户吗？</p>
            <br>
            <a href="#" id="yes_delete">确定</a>
            <a href="#" id="no_delete">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js" charset="UTF-8"></script>
