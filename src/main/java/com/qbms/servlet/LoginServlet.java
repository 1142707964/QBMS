package com.qbms.servlet;

import com.qbms.pojo.User;
import com.qbms.service.user.UserServiceImpl;
import com.qbms.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前端获取登录的用户名和密码
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");

        //和数据库中的密码进行对比，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userName, passWord);//此时已查询出是否存在该用户的信息

        //权限分离
        if (user != null) { //查有此人，可以登录
            request.getSession().setAttribute(Constants.USER_SESSION,user);//将用户信息存到sessoin中
            request.getSession().setAttribute(Constants.LOGIN_ROLE,user.getRole());//存储当前登录用户角色
            if (user.getRole().equals("管理员")){
                response.sendRedirect("jsp/admin/index.jsp");//跳转到管理员主页
            }else if (user.getRole().equals("老师")){
                response.sendRedirect("jsp/teacher/index.jsp");//跳转到老师主页
            }else if (user.getRole().equals("学委")){
                response.sendRedirect("jsp/xuewei/index.jsp");//跳转到学委主页
            }else if (user.getRole().equals("学生")){
                response.sendRedirect("jsp/normal/index.jsp");//跳转到普通主页
            }

        }else{  //查无此人，不予登录
            //转发回登录页面，顺带提示错误信息
//            request.getSession().setAttribute("error","用户名或密码不正确");
            request.setAttribute("error","用户名或密码不正确");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
