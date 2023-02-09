package com.qbms.service.course;

import com.qbms.dao.BaseDao;
import com.qbms.dao.course.CourseDao;
import com.qbms.dao.course.CourseDaoImpl;
import com.qbms.pojo.Course;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseServiceImpl implements CourseService {
    private CourseDao courseDao = null;
    public CourseServiceImpl() {
        courseDao = new CourseDaoImpl();
    }

    //新增课程
    @Override
    public boolean addCourse(Course course) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = courseDao.addCourse(conn,course);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("addCourse SUCCESS!");
            }else {
                System.out.println("addCourse FAILED!");
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

    //获取课程列表
    @Override
    public List<Course> getCourseList() throws Exception {
        Connection conn = null;
        List<Course> courseList = null;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            courseList = courseDao.getCourseList(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return courseList;
    }

    //删除课程
    @Override
    public boolean delCourse(int cid) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = courseDao.delCourse(conn,cid);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("delCourse SUCCESS!");
            }else {
                System.out.println("delCourse FAILED!");
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

    //JUnit测试1：新增课程
    @Test
    public void test1(){
        CourseServiceImpl courseService = new CourseServiceImpl();
        Course course = new Course();
        course.setName("人工智能");
        boolean b = courseService.addCourse(course);
        System.out.println(b);
    }

    //JUnit测试2：获取课程列表
    @Test
    public void test2() throws Exception {
        CourseServiceImpl courseService = new CourseServiceImpl();
        List<Course> courseList = courseService.getCourseList();
        for (Course course : courseList) {
            System.out.println(course.getName());
        }
    }

    //JUnit测试3：删除课程
    @Test
    public void test3() throws Exception {
        CourseServiceImpl courseService = new CourseServiceImpl();
        boolean b = courseService.delCourse(10);
        System.out.println(b);
    }
}
