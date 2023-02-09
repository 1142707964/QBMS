package com.qbms.dao.user;

import com.qbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //得到要登录的用户
    public User getLoginUser(Connection conn, String userName, String passWord) throws SQLException;

    //修改当前用户密码
    public int updatePwd(Connection conn, String newPwd, int id) throws SQLException;

    //新增用户
    public int addUser(Connection conn, User user) throws SQLException;

    //查询用户总数
    public int getUserCount(Connection conn,String username,String realname,String role) throws SQLException;

    //按条件查询用户（分页）
    List<User> getUserList(Connection conn, String username, String realname, String role, int currentPageNo, int pageSize) throws Exception;

    //删除用户
    public int delUser(Connection conn, int uid) throws SQLException;

    //修改用户角色
    public int updateUser(Connection conn, String newRole, int uid) throws SQLException;
}
