package com.ssdut411.app.questionanswer.model.Resp;


import com.ssdut411.app.questionanswer.model.model.TeacherHomeworkModel;

/**
 * Created by aass on 2016/4/7.
 */
public class PostHomeworkResp extends BaseResp {
    public static String DESC_NOT_ENOUGH = "题库中没有这么多题目";
    public static String DESC_SUCCESS = "获取成功";
    private TeacherHomeworkModel homeworkModel;

       public TeacherHomeworkModel getHomeworkModel() {
        return homeworkModel;
    }

    public void setHomeworkModel(TeacherHomeworkModel homeworkModel) {

        this.homeworkModel = homeworkModel;
    }

    public PostHomeworkResp() {

    }
}
