package com.qbms.servlet;

import com.qbms.pojo.User;
import com.qbms.service.user.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(value = "/register.do")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前端获取注册的信息
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String realName = request.getParameter("realname");
        String role = request.getParameter("role");
        System.out.println("[注册]userName："+userName);
        System.out.println("[注册]passWord："+passWord);
        System.out.println("[注册]realName："+realName);
        System.out.println("[注册]role："+role);

        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        user.setRealName(realName);
        user.setRole(role);

        UserServiceImpl userService = new UserServiceImpl();
        if (userService.register(user)){
            request.setAttribute("success","注册成功！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }else{
            request.setAttribute("error","注册失败！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
