package com.ssdut411.app.questionanswer.model.Resp;


import com.ssdut411.app.questionanswer.model.model.QuestionModel;

import java.util.List;
import java.util.Map;

/**
 * Created by LENOVO on 2016/3/8.
 */
public class GetCollectionsResp extends BaseResp {
    public static String DESC_SUCCESS = "获取成功";
    private List<String> pointList;
    private Map<String,List<QuestionModel>> questionList;
    public GetCollectionsResp() {
    }

    public List<String> getPointList() {
        return pointList;
    }

    public void setPointList(List<String> pointList) {
        this.pointList = pointList;
    }

    public Map<String, List<QuestionModel>> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Map<String, List<QuestionModel>> questionList) {
        this.questionList = questionList;
    }
}
