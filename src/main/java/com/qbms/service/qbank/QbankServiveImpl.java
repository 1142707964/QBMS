package com.qbms.service.qbank;

import com.qbms.dao.BaseDao;
import com.qbms.dao.qbank.QbankDao;
import com.qbms.dao.qbank.QbankDaoImpl;
import com.qbms.pojo.Question;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QbankServiveImpl implements QbankService {
    private QbankDao qbankDao = null;
    public QbankServiveImpl() {
        qbankDao = new QbankDaoImpl();
    }

    //新增题目
    @Override
    public boolean addQuestion(Question question) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = qbankDao.addQuestion(conn,question);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("addQuestion SUCCESS!");
            }else {
                System.out.println("addQuestion FAILED!");
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

    //通过条件查询题目列表
    @Override
    public List<Question> getQuestionList(String course, String type, String keyword, int currentPageNo, int pageSize) throws Exception {
        Connection conn = null;
        List<Question> questionList = null;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            questionList = qbankDao.getQuestionList(conn,course,type,keyword,currentPageNo,pageSize);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return questionList;
    }

    //查询符合条件的题目数
    @Override
    public int getQuestionCount(String course, String type, String keyword) {
        Connection conn = null;
        int questionCount = 0;
        try {
            conn = BaseDao.getConn();
            questionCount = qbankDao.getQuestionCount(conn, course, type, keyword);
            System.out.println("QbankServiceImpl--->getQuestionCount()--->questionCount："+questionCount);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return questionCount;
    }

    //删除题目
    @Override
    public boolean delQuestion(int qid) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = qbankDao.delQuestion(conn,qid);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("delQuestion SUCCESS!");
            }else {
                System.out.println("delQuestion FAILED!");
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

    //修改题目
    @Override
    public boolean updateQuestion(Question question) {
        Connection conn = null;
        boolean flag = false;
        int updateRows = 0;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            conn.setAutoCommit(false);//开启JDBC事务
            updateRows = qbankDao.updateQuestion(conn,question);
            conn.commit();//提交事务
            if (updateRows > 0){
                flag = true;
                System.out.println("updateQuestion SUCCESS!");
            }else {
                System.out.println("updateQuestion FAILED!");
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

    //根据题目id获取题目
    @Override
    public Question getQuestionById(int qid) throws Exception {
        Connection conn = null;
        Question question = null;

        try {
            conn = BaseDao.getConn();
            //通过业务层调用DAO层（具体的数据库操作）
            question = qbankDao.getQuestionById(conn,qid);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeConn(conn,null,null);
        }
        return question;
    }

    //JUnit测试1：新增题目
    @Test
    public void test1(){
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        Question question = new Question();
        question.setCourse("数据结构");
        question.setType("选择题");
        question.setKeyword("递归");
        question.setContent("对不对？");
        question.setAnswer("对！");
        boolean b = qbankServive.addQuestion(question);
        System.out.println(b);
    }

    //JUnit测试2：根据条件获取题目列表
    @Test
    public void test2() throws Exception {
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        List<Question> questionList = qbankServive.getQuestionList("","","",1,5);
        for (Question question : questionList) {
            System.out.println(question.getCourse());
        }
    }

    //JUnit测试3：删除题目
    @Test
    public void test3() throws Exception {
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        boolean b = qbankServive.delQuestion(2);
        System.out.println(b);
    }

    //JUnit测试4：修改题目
    @Test
    public void test4() throws Exception {
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        Question question = new Question();
        question.setCourse("数据结构");
        question.setType("选择题");
        question.setKeyword("递归");
        question.setContent("错不错？");
        question.setAnswer("对！");
        question.setId(1);
        boolean b = qbankServive.updateQuestion(question);
        System.out.println(b);
    }

    //JUnit测试5：根据题目id获取题目
    @Test
    public void test5() throws Exception {
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        Question question = qbankServive.getQuestionById(3);
        System.out.println(question.getAnswer());
    }

    //JUnit测试6：查询符合条件的题目数
    @Test
    public void test6() throws Exception {
        QbankServiveImpl qbankServive = new QbankServiveImpl();
        int questionCount = qbankServive.getQuestionCount("数据结构","","");
        System.out.println(questionCount);
    }
}
