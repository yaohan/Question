package com.ssdut411.app.questionanswer.model.Resp;


import com.ssdut411.app.questionanswer.model.model.ErrorQuestionModel;

import java.util.List;
import java.util.Map;

/**
 * Created by LENOVO on 2016/1/12.
 */
public class GetErrorsResp extends BaseResp {
    public static String DESC_SUCCESS = "获取成功";
    private List<String> pointList;
    private Map<String,List<ErrorQuestionModel>> questionList;
    public GetErrorsResp() {
    }

    public List<String> getPointList() {
        return pointList;
    }

    public void setPointList(List<String> pointList) {
        this.pointList = pointList;
    }

    public Map<String, List<ErrorQuestionModel>> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Map<String, List<ErrorQuestionModel>> questionList) {
        this.questionList = questionList;
    }
}
