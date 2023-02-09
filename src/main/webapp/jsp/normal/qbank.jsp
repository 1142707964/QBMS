<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/18
  Time: 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/normal/funclist.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是：</strong>
        <span>题库页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/jsp/qbank.do">
            <input name="method" value="query" class="input-text" type="hidden">
            <span>课程名：</span>
            <select name="queryCourseName">
                <option value="全部">全部</option>
                <c:forEach var="course" items="${queryCourseList}">
                    <option <c:if test="${course.name == queryCourseName}">selected</c:if>
                            value="${course.name}">${course.name}</option>
                </c:forEach>
<%--                <option value="全部">全部</option>--%>
<%--                <option value="数据结构" ${queryCourseName == "数据结构" ? "selected=\"selected\"":"" }>数据结构</option>--%>
<%--                <option value="操作系统" ${queryCourseName == "操作系统" ? "selected=\"selected\"":"" }>操作系统</option>--%>
<%--                <option value="编译原理" ${queryCourseName == "编译原理" ? "selected=\"selected\"":"" }>编译原理</option>--%>
            </select>
            <span>题目类型：</span>
            <select name="queryQuestionType">
                <option value="全部">全部</option>
                <option value="选择题" ${queryQuestionType == "选择题" ? "selected=\"selected\"":"" }>选择题</option>
                <option value="填空题" ${queryQuestionType == "填空题" ? "selected=\"selected\"":"" }>填空题</option>
                <option value="判断题" ${queryQuestionType == "判断题" ? "selected=\"selected\"":"" }>判断题</option>
                <option value="问答题" ${queryQuestionType == "问答题" ? "selected=\"selected\"":"" }>问答题</option>
            </select>

            <span>关键字：</span>
            <input type="text" name="queryKeyword" value="${queryKeyword}">

            <input type="hidden" name="pageIndex" value="1"/>
            <input	value="查 询" type="submit" id="searchbutton">
        </form>
    </div>
    <!--通用表格样式-->
    <table class="commonTable" cellpadding="0" cellspacing="0" style="table-layout: fixed">
        <tr class="firstTr">
            <th style="width: 10%; overflow: hidden">课程名</th>
            <th style="width: 10%; overflow: hidden">题目类型</th>
            <th style="width: 10%; overflow: hidden">关键字</th>
            <th style="width: 35%; overflow: hidden">题目描述</th>
            <th style="width: 35%; overflow: hidden">答案</th>
        </tr>
        <c:forEach var="question" items="${questionList}" varStatus="status">
            <tr>
                <td>
                    <span>${question.course}</span>
                </td>
                <td>
                    <span>${question.type}</span>
                </td>
                <td>
                    <span>${question.keyword}</span>
                </td>
                <td style="overflow: auto">
                    <span>${question.content}</span>
                </td>
                <td style="overflow: auto">
					<span>${question.answer}</span>
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

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>您确定要删除该题目吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/qbank.js"></script>