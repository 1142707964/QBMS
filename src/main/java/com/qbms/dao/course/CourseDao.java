package com.qbms.dao.course;

import com.qbms.pojo.Course;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CourseDao {
    //新增课程
    public int addCourse(Connection conn, Course course) throws SQLException;

    //获取课程列表
    List<Course> getCourseList(Connection conn) throws Exception;

    //删除课程
    public int delCourse(Connection conn, int cid) throws SQLException;



}
