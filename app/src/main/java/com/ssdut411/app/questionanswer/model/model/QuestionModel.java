package com.ssdut411.app.questionanswer.model.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 2015/12/29.
 */
public class QuestionModel implements Serializable{
    private String qId;
    private String stem;
    private List<String> options;
    private String answer;
    private String resolve;
    private List<String> testSites;
    private String myAnswer;
    private Boolean collection;

    public QuestionModel() {

    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getResolve() {
        return resolve;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public List<String> getTestSites() {
        return testSites;
    }

    public void setTestSites(List<String> testSites) {
        this.testSites = testSites;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }
}
