package com.qbms.dao.course;

import com.qbms.dao.BaseDao;
import com.qbms.pojo.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    //新增课程
    @Override
    public int addCourse(Connection conn, Course course) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "insert into course(`name`) values(?)";
            Object[] params = {course.getName()};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

    //获取课程列表
    @Override
    public List<Course> getCourseList(Connection conn) throws Exception {
        ArrayList<Course> courseList = new ArrayList<>();
        PreparedStatement prst = null;
        ResultSet rs = null;
        if (conn != null) {
            String sql = "select * from course";
            Object[] params = {};
            rs = BaseDao.execute(conn, prst, sql, params, rs);
            while (rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                courseList.add(course);
            }
            BaseDao.closeConn(null,prst,rs);
        }
        return courseList;
    }

    //删除课程
    @Override
    public int delCourse(Connection conn, int cid) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "delete from course where `id` = ?";
            Object[] params = {cid};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

}
