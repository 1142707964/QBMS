package com.qbms.servlet;

import com.alibaba.fastjson.JSONArray;
import com.qbms.pojo.User;
import com.qbms.service.user.UserService;
import com.qbms.service.user.UserServiceImpl;
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

@WebServlet(value = "/jsp/user.do")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String method = request.getParameter("method");
        System.out.println("UserServlet————————>method：【"+method+"】");
        if (method.equals("query")){
            queryUser(request,response);
        }else if (method.equals("validateOldPwd")){
            validateOldPwd(request,response);
        }else if (method.equals("savePwd")){
            savePwd(request,response);
        }else if (method.equals("delete")){
            deleteUser(request,response);
        }else if (method.equals("update")){
            updateUser(request,response);
        }
    }

    //验证旧密码（session中存有用户的密码）
    public void validateOldPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从Session中取得旧密码的值
        Object user = request.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = request.getParameter("oldpassword");

        //万能的Map：结果集
        HashMap<String, String> resultMap = new HashMap<>();

        if (user == null){ //Session失效了或者Session过期了
            resultMap.put("result","sessionError");
        }else if (oldpassword.equals("") || oldpassword==null){ //输入的密码为空
            resultMap.put("result","error");
        }else{
            String userPassword = ((User) user).getPassWord();//Session中用户的密码
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }

        try {
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            //JSONArray，阿里巴巴的工具类，转换格式
            /*
            resultMap = ["result","true","result","false"]
            Json格式 = {key:value}
             */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();//刷新缓冲区，清空缓冲区数据，再进行关闭
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //保存新密码
    public void savePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newpassword = request.getParameter("newpassword");
        System.out.println("UserServlet-------->newpassword："+newpassword);

        //从Session里面拿用户id
        Object user = request.getSession().getAttribute(Constants.USER_SESSION);
        boolean flag = false;

        if (user!=null && newpassword!=null && newpassword.length()!=0){
            UserServiceImpl userService = new UserServiceImpl();
            flag = userService.updatePwd(newpassword, ((User) user).getId());
            if (flag){
                request.setAttribute("message","密码修改成功！请退出使用新密码登录。");
                //密码修改成功，移除当前Session
                request.getSession().removeAttribute(Constants.USER_SESSION);
            }else{
                request.setAttribute("message","密码修改失败！");
            }
        }else{
            request.setAttribute("message","密码框为空，请输入密码！");
        }
        System.out.println("UserServlet-------->当前请求路径："+request.getRequestURI());
//        User session = (User) request.getSession();//null
//        System.out.println("当前用户："+session.getRealName());

        System.out.println("当前用户角色："+request.getSession().getAttribute(Constants.LOGIN_ROLE));
        //返回前端页面
        if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("管理员")){
            request.getRequestDispatcher("/jsp/admin/pwdmodify.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("老师")){
            request.getRequestDispatcher("/jsp/teacher/pwdmodify.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学委")){
            request.getRequestDispatcher("/jsp/xuewei/pwdmodify.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学生")){
            request.getRequestDispatcher("/jsp/normal/pwdmodify.jsp").forward(request,response);
        }

    }

    //查询用户和角色列表
    public void queryUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前端获取数据
        String queryUserName = request.getParameter("queryUserName");
        String queryRealName = request.getParameter("queryRealName");
        String queryUserRole = request.getParameter("queryUserRole");

        String pageIndex = request.getParameter("pageIndex");
        int currentPageNo = 1; //第一次进入这个请求，一定是第一页
        int pageSize = 5;//可以把页面容量写到配置文件中（db.properties），方便后期修改

        if (queryUserName == null){
            queryUserName = "";
        }
        if (queryRealName == null){
            queryRealName = "";
        }
        if (queryUserRole == null){
            if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("管理员")){
                queryUserRole = "管理员端";
            }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("老师")){
                queryUserRole = "老师端";
            }
        }

        if (pageIndex != null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        UserServiceImpl userService = new UserServiceImpl();
        //获取查询到的用户总数
        int userCount = userService.getUserCount(queryUserName, queryRealName, queryUserRole);

        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(userCount);
        int totalPageCount = pageSupport.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1 ){  //用户输入页数小于1，显示第一页数据
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){  //用户输入页数大于最后一页，显示最后一页数据
            currentPageNo = totalPageCount;
        }

        //获取查询到的用户列表
        List<User> userList = userService.getUserList(queryUserName, queryRealName, queryUserRole, currentPageNo, pageSize);
        request.getSession().setAttribute("userList",userList);

        //传递显示前端参数
        request.setAttribute("totalCount",userCount);
        request.setAttribute("totalPageCount",totalPageCount);
        request.setAttribute("currentPageNo",currentPageNo);
        request.setAttribute("queryUserName",queryUserName);
        request.setAttribute("queryRealName",queryRealName);
        request.setAttribute("queryUserRole",queryUserRole);

        //返回前端页面
        if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("管理员")){
            request.getRequestDispatcher("/jsp/admin/userlist.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("老师")){
            request.getRequestDispatcher("/jsp/teacher/userlist.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学委")){
            request.getRequestDispatcher("/jsp/xuewei/userlist.jsp").forward(request,response);
        }else if (request.getSession().getAttribute(Constants.LOGIN_ROLE).equals("学生")){
            request.getRequestDispatcher("/jsp/normal/userlist.jsp").forward(request,response);
        }

    }

    //删除用户
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        System.out.println("当前选定用户id："+uid);
        String realName = request.getParameter("realName");
        System.out.println("当前选定用户："+realName);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        UserService userService = new UserServiceImpl();
        if (userService.delUser(Integer.parseInt(uid))) {
            resultMap.put("deleteResult", "true");
            System.out.println("用户删除成功！");
        } else {
            resultMap.put("deleteResult", "false");
            System.out.println("用户删除失败！");
        }

        //把resultMap转换成json对象输出.
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();

        /*String uid = request.getParameter("uid");
        String uname = request.getParameter("uname");
        System.out.println("被删除用户："+uname);
        UserServiceImpl userService = new UserServiceImpl();
        boolean flag = userService.delUser(Integer.parseInt(uid));
        if (flag){
            System.out.println("用户删除成功！");
            try {
                response.sendRedirect("/jsp/user.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("用户删除失败！");
            try {
                request.getRequestDispatcher("/jsp/user.do?method=query").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    //修改用户角色
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uid = request.getParameter("uid");
        System.out.println("当前选定用户id："+uid);
        String newRole = request.getParameter("newRole");
        if (newRole == null){
            newRole = "";
        }
        System.out.println("当前选定角色："+newRole);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        UserService userService = new UserServiceImpl();
        if (userService.updateUser(newRole,Integer.parseInt(uid))) {
            resultMap.put("updateResult", "true");
            System.out.println("用户角色修改成功！");
        } else {
            resultMap.put("updateResult", "false");
            System.out.println("用户角色修改失败！");
        }

        //把resultMap转换成json对象输出.
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
