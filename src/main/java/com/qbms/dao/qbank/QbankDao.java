package com.qbms.dao.qbank;

import com.qbms.pojo.Question;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface QbankDao {
    //新增题目
    public int addQuestion(Connection conn, Question question) throws SQLException;

//    //获取题目列表（全部）
//    List<Question> getQuestionList(Connection conn) throws Exception;

    //按条件查找题目（分页实现）
    List<Question> getQuestionList(Connection conn, String course, String type, String keyword, int currentPageNo, int pageSize) throws Exception;

    //根据课程名或题目类型或关键字查询题目总数（配合分页使用）
    public int getQuestionCount(Connection conn,String course,String type,String keyword) throws SQLException;

    //删除题目
    public int delQuestion(Connection conn, int qid) throws SQLException;

    //修改题目
    public int updateQuestion(Connection conn, Question question) throws SQLException;

    //根据题目id获取题目
    public Question getQuestionById(Connection conn, int qid) throws SQLException;
}
