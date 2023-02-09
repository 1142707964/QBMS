<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/2/2
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/teacher/funclist.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是：</strong>
        <span>密码修改页面</span>
    </div>
    <div class="userAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath}/jsp/user.do">
            <input type="hidden" name="method" value="savePwd">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div class="info">${message}</div>
            <div class="">
                <label for="oldPassword">旧密码：</label>
                <input type="password" name="oldpassword" id="oldpassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="newPassword">新密码：</label>
                <input type="password" name="newpassword" id="newpassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="rnewpassword">确认新密码：</label>
                <input type="password" name="rnewpassword" id="rnewpassword" value="">
                <font color="red"></font>
            </div>
            <div class="userAddBtn">
                <!--<a href="#">保存</a>-->
                <input type="button" name="save" id="save" value="保存" class="input-button">
            </div>
        </form>
    </div>
</div>
</section>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/pwdmodify.js" charset="GBK"></script>