package com.ssdut411.app.questionanswer.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LENOVO on 2015/12/29.
 */
public class SelfTestModel implements Serializable{

    //自测ID
    private String id;
    //自测名称
    private String name;
    //学生ID
    private String psId;
    //自测状态
    private int state;
    //问题列表
    private List<QuestionModel> questionList;
    //答案列表
    private List<String> answerList;
    //答案正误
    private List<Boolean> resultList;
    //自测成绩
    private double grade;
    //知识点列表
    private List<String> testSitesList;

    private Date newTime;

    public SelfTestModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<QuestionModel> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionModel> questionList) {
        this.questionList = questionList;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public List<Boolean> getResultList() {
        return resultList;
    }

    public void setResultList(List<Boolean> resultList) {
        this.resultList = resultList;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public List<String> getTestSitesList() {
        return testSitesList;
    }

    public void setTestSitesList(List<String> testSitesList) {
        this.testSitesList = testSitesList;
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }
}
