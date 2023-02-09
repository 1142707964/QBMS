<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/28
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/admin/funclist.jsp"%>

<script type="text/javascript" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/ueditor/ueditor.all.js"></script>

<style type="text/css">
    .questionAdd{
        border: 1px solid #9ac70f;
        padding: 20px;
        margin: 20px;
    }
    .questionAdd label {
        width: 200px;
        display: inline-block;
        text-align: right;
    }
    .questionAdd input, .questionAdd select {
        width: 260px;
        height: 30px;
        line-height: 30px;
        border-radius: 4px;
        outline: none;
        padding-left: 10px;
        border: 1px solid #4987c0;
        box-shadow: 1px 1px 1px #99afc4;
        margin-top: 12px;
    }
    .questionAdd input:focus, .questionAdd select:focus {
        border: 1px solid #0e56a8;
        background: rgba(238, 236, 240, 0.2);
        -webkit-box-shadow: 0px 1px 4px 0px rgba(168, 168, 168, 0.9) inset;
        -moz-box-shadow: 0px 1px 4px 0px rgba(168, 168, 168, 0.9) inset;
        box-shadow: 0px 1px 4px 0px rgba(168, 168, 168, 0.9) inset;
    }
    #add{
        padding-right: 10px;
        padding-top: 0px;
    }
    #return{
        padding-left: 0px;
    }
</style>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>题库管理页面 >> 添加题目页面</span>
    </div>
    <div class="questionAdd">
        <form method="post" action="${pageContext.request.contextPath }/jsp/qbank.do">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <input type="hidden" name="method" value="add">
            <div>
                <label>课程名：</label>
                <select name="course">
<%--                    <option value="数据结构">数据结构</option>--%>
<%--                    <option value="操作系统">操作系统</option>--%>
<%--                    <option value="编译原理">编译原理</option>--%>
                    <c:forEach var="course" items="${queryCourseList}">
                        <option value="${course.name}">${course.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label>题目类型：</label>
                <select name="type">
                    <option value="选择题">选择题</option>
                    <option value="填空题">填空题</option>
                    <option value="判断题">判断题</option>
                    <option value="问答题">问答题</option>
                </select>
            </div>
            <div>
                <label for="keyword">关键字：</label>
                <input type="text" name="keyword" id="keyword" value="">
            </div>
            <div>
                <label for="content">题目描述：</label>
                <textarea id="content" name="content" style="width: 800px;height: 100px;margin: 0 auto;" required></textarea>

                <script type="text/javascript">
                    var ue = UE.getEditor('content',{
                        toolbars: [
                            ['fullscreen', 'source', 'undo', 'redo'],
                            ['simpleupload', 'insertimage', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
                        ],
                        // initialContent:"在这儿描述题目内容",
                        // focus:true,
                        // initialFrameWidth:800,
                        // initialFrameHeight:300,
                    });
                </script>
            </div>
            <div>
                <label for="answer">答案：</label>
                <textarea id="answer" name="answer" style="width: 800px;height: 100px;margin: 0 auto;" required></textarea>

                <script type="text/javascript">
                    var ue = UE.getEditor('answer',{
                        toolbars: [
                            ['fullscreen', 'source', 'undo', 'redo'],
                            ['simpleupload', 'insertimage', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
                        ],
                        // initialContent:"在这儿输入答案",
                        // focus:true,
                        // initialFrameWidth:800,
                        // initialFrameHeight:300,
                    });
                </script>
            </div>
            <div class="userAddBtn">
                <input type="submit" name="add" id="add" value="保存">
                <input type="button" name="back" id="return" value="返回">
            </div>
        </form>
    </div>
</div>

</section>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/qbank.js"></script>