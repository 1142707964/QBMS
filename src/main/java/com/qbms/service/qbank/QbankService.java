package com.qbms.service.qbank;

import com.qbms.pojo.Question;

import java.util.List;

public interface QbankService {
    //新增题目
    public boolean addQuestion(Question question);

    //通过条件查询题目列表
    List<Question> getQuestionList(String course, String type, String keyword, int currentPageNo, int pageSize) throws Exception;

    //查询符合条件的题目数
    public int getQuestionCount(String course, String type, String keyword);

    //删除题目
    public boolean delQuestion(int qid);

    //修改题目
    public boolean updateQuestion(Question question);

    //根据题目id获取题目
    Question getQuestionById(int qid) throws Exception;
}
