package com.qbms.service.user;

import com.qbms.pojo.User;

import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //根据用户id修改用户密码
    public boolean updatePwd(String newPwd, int id);

    //新增用户（用户注册）
    public boolean register(User user);

    //查询符合条件的用户数
    public int getUserCount(String username, String realname, String role);

    //通过条件查询用户列表
    public List<User> getUserList(String username, String realname, String role, int currentPageNo, int pageSize);

    //删除用户
    public boolean delUser(int uid);

    //修改用户角色
    public boolean updateUser(String newRole, int uid);

}
