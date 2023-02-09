package com.qbms.dao.user;

import com.mysql.jdbc.StringUtils;
import com.qbms.dao.BaseDao;
import com.qbms.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //得到要登录的用户
    @Override
    public User getLoginUser(Connection conn, String userName, String passWord) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        User user = null;
        if (conn != null) {
            String sql = "select * from user where username = ? and password = ?";
            Object[] params = {userName,passWord};
            rs = BaseDao.execute(conn, prst, sql, params, rs);

            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setPassWord(rs.getString("passWord"));
                user.setRealName(rs.getString("realName"));
                user.setRole(rs.getString("role"));
            }
            BaseDao.closeConn(null,prst,rs);
        }
        return user;
    }

    //修改当前用户密码
    @Override
    public int updatePwd(Connection conn, String newPwd, int id) throws SQLException {
        PreparedStatement prst = null;
        int updateRows = 0;
        if (conn != null){
            String sql = "update user set password = ? where id = ?";
            Object[] param = {newPwd,id};
            System.out.println("UserDaoImpl-->updatePwd()："+sql.toString());//输出完整的SQL语句，方便调试
            updateRows = BaseDao.execute(conn, prst, sql, param, updateRows);

            BaseDao.closeConn(null,prst,null);
        }
        return updateRows;
    }

    //新增用户
    @Override
    public int addUser(Connection conn, User user) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "insert into user(`username`,`password`,`realname`,`role`) values(?,?,?,?)";
            Object[] params = {user.getUserName(),user.getPassWord(),user.getRealName(),user.getRole()};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

    //根据用户名或姓名或角色名查询用户总数
    @Override
    public int getUserCount(Connection conn, String username, String realname, String role) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int count = 0;
        if (conn != null){
            StringBuffer sql = new StringBuffer();//SQL语句
            sql.append("select count(*) as count from user");
            ArrayList<Object> list = new ArrayList<>();//存放参数

            if (role.equals("管理员端")){
                sql.append(" where (role = '老师' or role = '学委' or role = '学生')");
            }else if (role.equals("老师端")){
                sql.append(" where (role = '学委' or role = '学生')");
            }else{
                sql.append(" where role = ?");
                list.add(role);
            }

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and username like ?");
                list.add("%"+username+"%");
            }

            if (!StringUtils.isNullOrEmpty(realname)){
                sql.append(" and realname like ?");
                list.add("%"+realname+"%");
            }

            //将list转换为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl-->getUserCount()："+sql.toString());//输出完整的SQL语句，方便调试

            rs = BaseDao.execute(conn, prst, sql.toString(), params, rs);
            if (rs.next()){
                count = rs.getInt("count");//从结果集中获取查询到的数量
            }
            BaseDao.closeConn(null,prst,rs);
        }

        return count;
    }

    //按条件查询用户（分页）
    @Override
    public List<User> getUserList(Connection conn, String username, String realname, String role, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement prst = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        if (conn != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from user");
            ArrayList<Object> list = new ArrayList<>();//存放参数

            if (role.equals("管理员端")){
                sql.append(" where (role = '老师' or role = '学委' or role = '学生')");
            }else if (role.equals("老师端")){
                sql.append(" where (role = '学委' or role = '学生')");
            }else{
                sql.append(" where role = ?");
                list.add(role);
            }

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and username like ?");
                list.add("%"+username+"%");//index：0
                /*
                sql.append(" and u.username like %?%");//经测试，这样写也能查询到结果
                list.add(username);//index：0
                */
            }

            if (!StringUtils.isNullOrEmpty(realname)){
                sql.append(" and realname like ?");
                list.add("%"+realname+"%");//index：0
                /*
                sql.append(" and u.username like %?%");//经测试，这样写也能查询到结果
                list.add(username);//index：0
                */
            }

            /*
            在数据库中，分页使用limit(startIndex,pageSize)
            (0,5)   第1页   第0条数据    1 2 3 4 5
            (5,5)   第2页   第5条数据    6 7 8 9 10
            (10,5)  第3页   第10条数据   11 12 13 14 15
            当前页 = （当前页-1）* 页面大小
             */
            sql.append(" order by id asc limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;//limit 第几条数据，页面容量
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("UserDaoImpl-->getUserList()："+sql.toString());//输出完整的SQL语句，方便调试
            rs = BaseDao.execute(conn,prst,sql.toString(),params,rs);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassWord(rs.getString("password"));
                user.setRealName(rs.getString("realname"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }
            BaseDao.closeConn(null,prst,rs);
        }
        return userList;
    }

    //删除用户
    @Override
    public int delUser(Connection conn, int uid) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "delete from user where `id` = ?";
            Object[] params = {uid};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

    //修改用户角色
    @Override
    public int updateUser(Connection conn, String newRole, int uid) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "update user set role = ? where `id` = ?";
            Object[] params = {newRole,uid};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }


}

