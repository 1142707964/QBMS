package com.qbms.service.course;

import com.qbms.pojo.Course;

import java.util.List;

public interface CourseService {
    //新增课程
    public boolean addCourse(Course course);

    //获取课程列表
    List<Course> getCourseList() throws Exception;

    //删除课程
    public boolean delCourse(int cid);
}
