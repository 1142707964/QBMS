package com.qbms.servlet;

import com.alibaba.fastjson.JSONArray;
import com.qbms.pojo.Course;
import com.qbms.pojo.Question;
import com.qbms.service.course.CourseServiceImpl;
import com.qbms.service.qbank.QbankServiveImpl;
import com.qbms.util.Constants;
import com.qbms.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "QbankServlet", value = "/jsp/qbank.do")
public class QbankServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        response.setContentType("text/html;charset=utf8");
        String method = request.getParameter("method");
        System.out.println("QbankServlet————————>method：【"+method+"】");

        CourseServiceImpl courseService = new CourseServiceImpl();
        List<Course> courseList = null;
        try {
            courseList = courseService.getCourseList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //进入请求先获取到数据库中course表的所有课程，然后存储在浏览器中再赋予前端实现动态获取课程的搜索下拉框
        request.getSession().setAttribute("queryCourseList",courseList);
//        request.setAttribute("queryCourseList",courseList);//错误。必须存在session中，由于有两个请求（query和add）需要用到该属性，但进入题库管理主页后请求就结束了，所以add请求将会失效。

        if (method.equals("query")){
            try {
                queryQank(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (method.equals("add")){
            addQuestion(request,response);
        }else if (method.equals("delete")){
            deleteQuestion(request,response);
        }else if (method.equals("modify")){
            modifyQuestion(request,response);
        }else if (method.equals("getQId")){
            try {
                getQuestionById(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //按条件查询题库并实现分页
    public void queryQank(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        CourseServiceImpl courseService = new CourseServiceImpl();
//        List<Course> courseList = courseService.getCourseList();
//        //进入请求先获取到数据库中course表的所有课程，然后存储在浏览器中再赋予前端实现动态获取课程的搜索下拉框
//        request.setAttribute("queryCourseList",courseList);

        //从前端获取数据
        String queryCourseName = request.getParameter("queryCourseName");
        String queryQuestionType = request.getParameter("queryQuestionType");
        String queryKeyword = request.getParameter("queryKeyword");
        System.out.println("查询课程名："+queryCourseName);
        System.out.println("查询题目类型："+queryQuestionType);
        System.out.println("查询关键字："+queryKeyword);

        String pageIndex = request.getParameter("pageIndex");
        int currentPageNo = 1; //第一次进入这个请求，一定是第一页
        int pageSize = 5;//可以把页面容量写到配置文件中（db.properties），方便后期修改

        if (queryCourseName == null || queryCourseName.equals("全部")){
            queryCourseName = "";
        }

        if (queryQuestionType == null || queryQuestionType.equals("全部")){
            queryQuestionType = "";
        }

        if (queryKeyword == null){
            queryKeyword = "";
        }

        if (pageIndex != null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        QbankServiveImpl qbankServive = new QbankServiveImpl();
        //获取查询到的题目总数
        int questionCount = qbankServive.getQuestionCount(queryCourseName,queryQuestionType,queryKeyword);

        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(questionCount);
        int totalPageCount = pageSupport.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1 ){  //用户输入页数小于1，显示第一页数据
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){  //用户输入页数大于最后一页，显示最后一页数据
            currentPageNo = totalPageCount;
        }

        //获取查询到的用户列表
        List<Question> questionList = qbankServive.getQuestionList(queryCourseName,queryQuestionType,queryKeyword,currentPageNo,pageSize);
        request.setAttribute("questionList",questionList);

        //传递显示前端参数
        request.setAttribute("totalCount",questionCount);
        request.setAttribute("totalPageCount",totalPageCount);
        request.setAttribute("currentPageNo",currentPageNo);
        request.setAttribute("queryCourseName",queryCourseName);
        request.setAttribute("queryQuestionType",queryQuestionType);
        request.setAttribute("queryKeyword",queryKeyword);


        //返回前端页面
        if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("管理员")){
            request.getRequestDispatcher("/jsp/admin/qbank.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("老师")){
            request.getRequestDispatcher("/jsp/teacher/qbank.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学委")){
            request.getRequestDispatcher("/jsp/xuewei/qbank.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学生")){
            request.getRequestDispatcher("/jsp/normal/qbank.jsp").forward(request,response);
        }
    }

    //新增题目
    public void addQuestion(HttpServletRequest request, HttpServletResponse response){
        String course = request.getParameter("course");
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String content = request.getParameter("content");
        String answer = request.getParameter("answer");

        System.out.println("课程名："+course);
        System.out.println("题目类型："+type);
        System.out.println("关键字："+keyword);
        System.out.println("题目描述："+content);
        System.out.println("答案："+answer);

        Question question = new Question();
        question.setCourse(course);
        question.setType(type);
        question.setKeyword(keyword);
        question.setContent(content);
        question.setAnswer(answer);
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        boolean flag = qbankServive.addQuestion(question);
        System.out.println("新增题目结果："+flag);
        if (flag){
            try {
                response.sendRedirect("/jsp/qbank.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                request.getRequestDispatcher("/jsp/qbank.do?method=query").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //删除题目
    public void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String qid = request.getParameter("qid");
        System.out.println("删除第【"+qid+"】条题目");

        HashMap<String, String> resultMap = new HashMap<String, String>();
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        if (qbankServive.delQuestion(Integer.parseInt(qid))) {
            resultMap.put("delResult", "true");
            System.out.println("题目删除成功！");
        } else {
            resultMap.put("delResult", "false");
            System.out.println("题目删除失败！");
        }

        //把resultMap转换成json对象输出.
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //修改题目
    public void modifyQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String qid = request.getParameter("qid");
        System.out.println("修改第【"+qid+"】条题目");
        String course = request.getParameter("course");
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String content = request.getParameter("content");
        String answer = request.getParameter("answer");

        System.out.println("[修改]course："+course);
        System.out.println("[修改]type："+type);
        System.out.println("[修改]keyword："+keyword);
        System.out.println("[修改]content："+content);
        System.out.println("[修改]answer："+answer);

        Question question = new Question();
        question.setId(Integer.parseInt(qid));
        question.setCourse(course);
        question.setType(type);
        question.setKeyword(keyword);
        question.setContent(content);
        question.setAnswer(answer);

        QbankServiveImpl qbankServive = new QbankServiveImpl();
        if (qbankServive.updateQuestion(question)) {
            System.out.println("题目修改成功！");
            request.getRequestDispatcher("/jsp/qbank.do?method=query").forward(request,response);
        } else {
            System.out.println("题目修改失败！");
            request.getRequestDispatcher("/jsp/qbank.do?method=query").forward(request,response);
        }
    }

    //根据题目id获取题目
    public void getQuestionById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String qid = request.getParameter("qid");
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        Question question = qbankServive.getQuestionById(Integer.parseInt(qid));
        request.setAttribute("question",question);
        request.getRequestDispatcher("/jsp/admin/qmodify.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
