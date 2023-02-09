package com.qbms.dao.qbank;

import com.mysql.jdbc.StringUtils;
import com.qbms.dao.BaseDao;
import com.qbms.pojo.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QbankDaoImpl implements QbankDao {
    //新增题目
    @Override
    public int addQuestion(Connection conn, Question question) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "insert into qbank(`course`,`type`,`keyword`,`content`,`answer`) values(?,?,?,?,?)";
            Object[] params = {question.getCourse(),question.getType(),question.getKeyword(),question.getContent(),question.getAnswer()};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

    //按条件查找题目获取题目列表（分页实现）
    @Override
    public List<Question> getQuestionList(Connection conn, String course, String type, String keyword, int currentPageNo, int pageSize) throws Exception {
        ArrayList<Question> questionList = new ArrayList<>();
        PreparedStatement prst = null;
        ResultSet rs = null;
        if (conn != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from qbank where 1=1");
            ArrayList<Object> list = new ArrayList<>();//存放参数

            if (!StringUtils.isNullOrEmpty(course)){
                sql.append(" and course like ?");
                list.add(course);//index：0
            }

            if (!StringUtils.isNullOrEmpty(type)){
                sql.append(" and type like ?");
                list.add(type);//index：1
            }

            if (!StringUtils.isNullOrEmpty(keyword)){
                sql.append(" and keyword like ?");
                list.add("%"+keyword+"%");//index：2
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
            System.out.println("QbankDaoImpl-->getQuestionList()："+sql.toString());//输出完整的SQL语句，方便调试
            rs = BaseDao.execute(conn, prst, sql.toString(), params, rs);
            while (rs.next()){
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setCourse(rs.getString("course"));
                question.setType(rs.getString("type"));
                question.setKeyword(rs.getString("keyword"));
                question.setContent(rs.getString("content"));
                question.setAnswer(rs.getString("answer"));
                questionList.add(question);
            }
            BaseDao.closeConn(null,prst,rs);
        }
        return questionList;
    }

    //根据课程名或题目类型或关键字查询题目总数（配合分页使用）
    @Override
    public int getQuestionCount(Connection conn, String course, String type, String keyword) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int count = 0;
        if (conn != null){
            StringBuffer sql = new StringBuffer();//SQL语句
            sql.append("select count(*) as count from qbank where 1=1");
            ArrayList<Object> list = new ArrayList<>();//存放参数

            if (!StringUtils.isNullOrEmpty(course)){
                sql.append(" and course like ?");
                list.add(course);//index：0
            }

            if (!StringUtils.isNullOrEmpty(type)){
                sql.append(" and type like ?");
                list.add(type);
            }

            if (!StringUtils.isNullOrEmpty(keyword)){
                sql.append(" and keyword like ?");
                list.add("%"+keyword+"%");
            }

            //将list转换为数组
            Object[] params = list.toArray();
            System.out.println("QbankDaoImpl-->getQuestionCount()："+sql.toString());//输出完整的SQL语句，方便调试

            rs = BaseDao.execute(conn, prst, sql.toString(), params, rs);
            if (rs.next()){
                count = rs.getInt("count");//从结果集中获取查询到的数量
            }
            BaseDao.closeConn(null,prst,rs);
        }

        return count;
    }

    //删除题目
    @Override
    public int delQuestion(Connection conn, int qid) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "delete from qbank where `id` = ?";
            Object[] params = {qid};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

    //修改题目
    @Override
    public int updateQuestion(Connection conn, Question question) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int updateRows = 0;
        if (conn != null) {
            String sql = "update qbank set `course`=?,`type`=?,`keyword`=?,`content`=?,`answer`=? where `id`=?";
            Object[] params = {question.getCourse(),question.getType(),question.getKeyword(),
                               question.getContent(),question.getAnswer(),question.getId()};
            updateRows = BaseDao.execute(conn, prst, sql, params, updateRows);

            BaseDao.closeConn(null,prst,rs);
        }
        return updateRows;
    }

    //根据题目id获取题目
    @Override
    public Question getQuestionById(Connection conn, int qid) throws SQLException {
        Question question = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        if (conn != null) {
            String sql = "select * from qbank where `id`=?";
            Object[] params = {qid};
            rs = BaseDao.execute(conn, prst, sql, params, rs);
            if (rs.next()){
                question = new Question();
                question.setId(rs.getInt("id"));
                question.setCourse(rs.getString("course"));
                question.setType(rs.getString("type"));
                question.setKeyword(rs.getString("keyword"));
                question.setContent(rs.getString("content"));
                question.setAnswer(rs.getString("answer"));
            }
            BaseDao.closeConn(null,prst,rs);
        }
        return question;
    }
}
