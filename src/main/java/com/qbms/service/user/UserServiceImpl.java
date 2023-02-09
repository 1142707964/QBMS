package com.qbms.service.user;

import com.qbms.dao.BaseDao;
import com.qbms.dao.user.UserDao;
import com.qbms.dao.user.UserDaoImpl;
import com.qbms.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{
    //业务层都会调DAO层，所以我们要引入DAO层
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();//接口new实现类，多态操作
    }
    //用户登录
    @Override
    public User login(String userName, String passWord) {
        Connection conn = null;
        User user = null;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            user = userDao.getLoginUser(conn, userName,passWord);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return user;
    }

    //更改密码
    @Override
    public boolean updatePwd(String newPwd, int id) {
        Connection conn = null;
        boolean flag = false;

        try {
            conn = BaseDao.getConn();
            if (userDao.updatePwd(conn, newPwd, id) > 0){
                flag = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return flag;
    }

    //新增用户（用户注册）
    @Override
    public boolean register(User user) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = userDao.addUser(conn,user);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("addUser SUCCESS!");
            }else {
                System.out.println("addUser FAILED!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return flag;
    }

    //查询符合条件的用户数
    @Override
    public int getUserCount(String username, String realname, String role) {
        Connection conn = null;
        int userCount = 0;
        try {
            conn = BaseDao.getConn();
            userCount = userDao.getUserCount(conn, username, realname, role);
            System.out.println("UserServiceImpl--->getUserCount()--->userCount："+userCount);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return userCount;
    }

    //通过条件查询用户列表
    @Override
    public List<User> getUserList(String username, String realname, String role, int currentPageNo, int pageSize) {
        Connection conn = null;
        List<User> userList = null;
        System.out.println("UserServiceImpl-->getUserList()-->username："+username);
        System.out.println("UserServiceImpl-->getUserList()-->userRole："+role);
        System.out.println("UserServiceImpl-->getUserList()-->currentPageNo："+currentPageNo);
        System.out.println("UserServiceImpl-->getUserList()-->pageSize："+pageSize);
        try {
            conn = BaseDao.getConn();
            userList = userDao.getUserList(conn, username, realname, role, currentPageNo, pageSize);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeConn(conn,null,null);
        }
        return userList;
    }

    //删除用户
    @Override
    public boolean delUser(int uid) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = userDao.delUser(conn,uid);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("delUser SUCCESS!");
            }else {
                System.out.println("delUser FAILED!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return flag;
    }

    //修改用户角色
    @Override
    public boolean updateUser(String newRole, int uid) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = userDao.updateUser(conn,newRole,uid);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("updateUser SUCCESS!");
            }else {
                System.out.println("updateUser FAILED!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return flag;
    }

    //JUnit测试：用户登录
    @Test
    public void test1(){
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin","123456");
        System.out.println(admin.getPassWord());
    }

    //JUnit测试：更改密码
    @Test
    public void test2(){
        UserServiceImpl userService = new UserServiceImpl();
        boolean flag = userService.updatePwd("0", 1);
        System.out.println(flag);
    }

    //JUnit测试：查询用户数量
    @Test
    public void test3(){
        UserServiceImpl userService = new UserServiceImpl();
        int userCount = userService.getUserCount("t","","全部");
//        int userCount = userService.getUserCount(null,null,"学生");
//        int userCount = userService.getUserCount("张", 3);
        System.out.println(userCount);
    }

    //JUnit测试：查询用户列表
    @Test
    public void test4(){
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = userService.getUserList("", "", "全部",1, 5);
        for (User user : userList) {
            System.out.println(user.getUserName()+" "+user.getRealName()+" "+user.getRole());
        }
    }

    //JUnit测试：删除用户
    @Test
    public void test5(){
        UserServiceImpl userService = new UserServiceImpl();
        boolean flag = userService.delUser(16);
        System.out.println(flag);
    }

    //JUnit测试：修改用户角色
    @Test
    public void test6(){
        UserServiceImpl userService = new UserServiceImpl();
        boolean flag = userService.updateUser("老师",2);
        System.out.println(flag);
    }

}
