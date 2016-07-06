package com.ssdut411.app.questionanswer.model.model;


/**
 * Created by LENOVO on 2016/1/26.
 */
public class ErrorQuestionModel {
    private int errorCount;
    private QuestionModel questionModel;

    public ErrorQuestionModel() {
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public QuestionModel getQuestionModel() {
        return questionModel;
    }

    public void setQuestionModel(QuestionModel questionModel) {
        this.questionModel = questionModel;
    }
}
