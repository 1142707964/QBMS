package com.qbms.servlet;

import com.qbms.pojo.Course;
import com.qbms.service.course.CourseServiceImpl;
import com.qbms.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/jsp/course.do")
public class CourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        System.out.println("CourseServlet————————>method：【"+method+"】");
        if (method.equals("query")){
            try {
                queryCourse(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (method.equals("add")){
            try {
                addCourse(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (method.equals("delete")){
            deleteCourse(request,response);
        }

    }

    //查询课程
    public void queryCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseServiceImpl courseService = new CourseServiceImpl();
        List<Course> courseList = courseService.getCourseList();
        request.getSession().setAttribute("courseList",courseList);
        //返回前端页面
        if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("管理员")){
            request.getRequestDispatcher("/jsp/admin/courselist.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("老师")){
            request.getRequestDispatcher("/jsp/teacher/courselist.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学委")){
            request.getRequestDispatcher("/jsp/xuewei/courselist.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学生")){
            request.getRequestDispatcher("/jsp/normal/courselist.jsp").forward(request,response);
        }
    }

    //新增课程
    public void addCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String newCourse = request.getParameter("newCourse");
        System.out.println("预新增课程："+newCourse);
        List<Course> courseList = new CourseServiceImpl().getCourseList();
        for (Course c : courseList) {
            if (newCourse.equals(c.getName())){
                System.out.println("重复添加课程！操作失败！");
                request.getRequestDispatcher("/jsp/course.do?method=query").forward(request,response);
                return;
            }
        }
        Course course = new Course();
        course.setName(newCourse);
        CourseServiceImpl courseService = new CourseServiceImpl();
        boolean flag = courseService.addCourse(course);
        System.out.println("新增课程结果："+flag);
        if (flag){
            request.getRequestDispatcher("/jsp/course.do?method=query").forward(request,response);
        }else{
            request.getRequestDispatcher("/jsp/course.do?method=query").forward(request,response);
        }

    }

    //删除课程
    public void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        System.out.println("被删除课程："+cname);
        CourseServiceImpl courseService = new CourseServiceImpl();
        boolean flag = courseService.delCourse(Integer.parseInt(cid));
        if (flag){
            System.out.println("课程删除成功！");
            try {
                response.sendRedirect("/jsp/course.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("课程删除失败！");
            try {
                request.getRequestDispatcher("/jsp/course.do?method=query").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
